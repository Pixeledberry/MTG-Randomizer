import UIKit
import WebKit

/// Serves the bundled page from mtgrandomizer://app/ so decks and settings keep a stable storage origin.
final class SchemeHandler: NSObject, WKURLSchemeHandler {
    func webView(_ webView: WKWebView, start task: WKURLSchemeTask) {
        guard let url = task.request.url else { return }
        var name = url.lastPathComponent
        if name.isEmpty || name == "/" { name = "index.html" }
        let base = (name as NSString).deletingPathExtension
        let ext = (name as NSString).pathExtension
        guard let file = Bundle.main.url(forResource: base, withExtension: ext),
              let data = try? Data(contentsOf: file) else {
            task.didFailWithError(URLError(.fileDoesNotExist)); return
        }
        let types = ["html": "text/html", "json": "application/json", "png": "image/png", "svg": "image/svg+xml"]
        let headers = ["Content-Type": (types[ext] ?? "application/octet-stream") + "; charset=utf-8",
                       "Content-Length": "\(data.count)"]
        let response = HTTPURLResponse(url: url, statusCode: 200, httpVersion: nil, headerFields: headers)!
        task.didReceive(response)
        task.didReceive(data)
        task.didFinish()
    }
    func webView(_ webView: WKWebView, stop task: WKURLSchemeTask) {}
}

final class MainViewController: UIViewController, WKScriptMessageHandler, WKNavigationDelegate, WKUIDelegate {
    private var web: WKWebView!

    override func loadView() {
        let config = WKWebViewConfiguration()
        config.setURLSchemeHandler(SchemeHandler(), forURLScheme: "mtgrandomizer")
        let controller = WKUserContentController()
        controller.add(self, name: "ios")
        // The page already knows how to talk to a native "Android" bridge, so we give it the same shape.
        let shim = "window.Android={share:function(t){window.webkit.messageHandlers.ios.postMessage({a:'share',t:t})}};"
        controller.addUserScript(WKUserScript(source: shim, injectionTime: .atDocumentStart, forMainFrameOnly: true))
        config.userContentController = controller
        web = WKWebView(frame: .zero, configuration: config)
        web.navigationDelegate = self
        web.uiDelegate = self
        web.allowsBackForwardNavigationGestures = true   // swipe from the left edge = back (closes the full-screen card and lists)
        web.scrollView.contentInsetAdjustmentBehavior = .never   // the page handles safe areas itself
        view = web
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        web.load(URLRequest(url: URL(string: "mtgrandomizer://app/index.html")!))
    }

    // Deck lists and backups go to the iOS share sheet (save to Files, send to yourself, ...).
    func userContentController(_ c: WKUserContentController, didReceive message: WKScriptMessage) {
        guard let body = message.body as? [String: Any], let action = body["a"] as? String else { return }
        if action == "share", let text = body["t"] as? String {
            let sheet = UIActivityViewController(activityItems: [text], applicationActivities: nil)
            sheet.popoverPresentationController?.sourceView = view
            sheet.popoverPresentationController?.sourceRect = CGRect(x: view.bounds.midX, y: view.bounds.midY, width: 0, height: 0)
            present(sheet, animated: true)
        }
    }

    // Links to websites (for example Scryfall card pages) open in Safari
    func webView(_ webView: WKWebView, decidePolicyFor action: WKNavigationAction,
                 decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        if let url = action.request.url, url.scheme == "http" || url.scheme == "https" {
            UIApplication.shared.open(url); decisionHandler(.cancel)
        } else { decisionHandler(.allow) }
    }
}
