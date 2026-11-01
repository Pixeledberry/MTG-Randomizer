package com.pixeledberry.mtgrandomizer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.FileProvider
import androidx.webkit.WebViewAssetLoader
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {
    private lateinit var web: WebView
    private val host = "appassets.androidplatform.net"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        setContentView(web)

        // Serves the bundled page (index.html and tags.json) over https so it runs in a secure context.
        val loader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()

        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true // decks, history and settings are saved here
        web.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? =
                loader.shouldInterceptRequest(request.url)

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (request.url.host == host) return false
                startActivity(Intent(Intent.ACTION_VIEW, request.url)) // external links (e.g. Scryfall) open in the browser
                return true
            }
        }
        web.addJavascriptInterface(Bridge(), "Android")
        web.loadUrl("https://$host/assets/index.html")
    }

    // The page keeps the full-screen card, lists and menus in its history, so Back closes them first.
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack() else super.onBackPressed()
    }

    // Sends an update status message to the page (onUpdateError, onUpdateReady, ...).
    private fun updateJs(fn: String, message: String?) {
        runOnUiThread { web.evaluateJavascript("window.$fn && window.$fn(${JSONObject.quote(message ?: "")})", null) }
    }

    inner class Bridge {
        // Deck lists and backups go to Android's share sheet (save to Files, send to yourself, ...).
        @JavascriptInterface
        fun share(text: String) {
            runOnUiThread {
                val send = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                startActivity(Intent.createChooser(send, "Share"))
            }
        }

        // ---- In-app updates ----
        // True when Android lets MTG Randomizer open the installer ("Install unknown apps" is allowed for it).
        @JavascriptInterface
        fun canInstallUpdates(): Boolean = packageManager.canRequestPackageInstalls()

        // Opens the Android settings page where the user can allow MTG Randomizer to install updates.
        @JavascriptInterface
        fun allowUpdates() {
            runOnUiThread {
                try {
                    startActivity(Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:$packageName")))
                } catch (e: Exception) {
                    updateJs("onUpdateError", e.message)
                }
            }
        }

        // Downloads the new APK from the project's GitHub releases, then opens Android's installer.
        // Android always asks the user to confirm. The fixed signing key lets it install over this version and keep all data.
        @JavascriptInterface
        fun installUpdate(url: String) {
            // Only APKs from the owner's own GitHub account are accepted. Any repo name is allowed, so a renamed repo keeps working.
            val official = Regex("^https://github\\.com/pixeledberry/[A-Za-z0-9._-]+/releases/download/[^?#]+\\.apk$", RegexOption.IGNORE_CASE)
            if (!official.matches(url)) {
                updateJs("onUpdateError", "not an official MTG Randomizer download")
                return
            }
            Thread {
                try {
                    val dir = File(cacheDir, "updates").apply { mkdirs() }
                    dir.listFiles()?.forEach { it.delete() }
                    val file = File(dir, "MTGRandomizer-update.apk")
                    val conn = URL(url).openConnection() as HttpURLConnection
                    conn.instanceFollowRedirects = true
                    conn.connectTimeout = 15000
                    conn.readTimeout = 30000
                    if (conn.responseCode != HttpURLConnection.HTTP_OK) throw Exception("HTTP ${conn.responseCode}")
                    val total = conn.contentLengthLong
                    conn.inputStream.use { input ->
                        file.outputStream().use { output ->
                            val buf = ByteArray(64 * 1024)
                            var done = 0L
                            var last = -1
                            while (true) {
                                val n = input.read(buf)
                                if (n < 0) break
                                output.write(buf, 0, n)
                                done += n
                                if (total > 0) {
                                    val pct = (done * 100 / total).toInt()
                                    if (pct != last) {
                                        last = pct
                                        runOnUiThread { web.evaluateJavascript("window.onUpdateProgress && window.onUpdateProgress($pct)", null) }
                                    }
                                }
                            }
                        }
                    }
                    conn.disconnect()
                    runOnUiThread {
                        try {
                            val uri = FileProvider.getUriForFile(this@MainActivity, "$packageName.files", file)
                            val install = Intent(Intent.ACTION_VIEW)
                                .setDataAndType(uri, "application/vnd.android.package-archive")
                                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            web.evaluateJavascript("window.onUpdateReady && window.onUpdateReady()", null)
                            startActivity(install)
                        } catch (e: Exception) {
                            updateJs("onUpdateError", e.message)
                        }
                    }
                } catch (e: Exception) {
                    updateJs("onUpdateError", e.message)
                }
            }.start()
        }
    }
}
