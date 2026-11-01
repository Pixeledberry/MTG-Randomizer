import UIKit
import WebKit

/// Serves the bundled page from mtgrandomizer://app/ so the roll history keeps a stable storage origin.
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
        let types = ["html": "text/html", "png": "image/png", "svg": "image/svg+xml"]
        let headers = ["Content-Type": (types[ext] ?? "application/octet-stream") + "; charset=utf-8",
                       "Content-Length": "\(data.count)"]
        let response = HTTPURLResponse(url: url, statusCode: 200, httpVersion: nil, headerFields: headers)!
        task.didReceive(response)
        task.didReceive(data)
        task.didFinish()
    }
    func webView(_ webView: WKWebView, stop task: WKURLSchemeTask) {}
}

final class MainViewController: UIViewController, WKNavigationDelegate, WKUIDelegate {
    private var web: WKWebView!

    override func loadView() {
        let config = WKWebViewConfiguration()
        config.setURLSchemeHandler(SchemeHandler(), forURLScheme: "mtgrandomizer")
        web = WKWebView(frame: .zero, configuration: config)
        web.navigationDelegate = self
        web.allowsBackForwardNavigationGestures = true
        web.scrollView.contentInsetAdjustmentBehavior = .never   // the page handles safe areas itself
        view = web
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        web.load(URLRequest(url: URL(string: "mtgrandomizer://app/index.html")!))
    }

    // Links to websites (for example Scryfall card pages) open in Safari
    func webView(_ webView: WKWebView, decidePolicyFor action: WKNavigationAction,
                 decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        if let url = action.request.url, url.scheme == "http" || url.scheme == "https" {
            UIApplication.shared.open(url); decisionHandler(.cancel)
        } else { decisionHandler(.allow) }
    }
}
