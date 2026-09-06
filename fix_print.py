import re

with open("app/src/main/java/com/example/util/WorksheetPrintManager.kt", "r") as f:
    content = f.read()

old_client = """        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                printJob = printManager.print(
                    jobName,
                    view.createPrintDocumentAdapter(jobName),
                    PrintAttributes.Builder().build()
                )
            }
        }"""

new_client = """        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                printJob = printManager.print(
                    jobName,
                    view.createPrintDocumentAdapter(jobName),
                    PrintAttributes.Builder().build()
                )
            }
            override fun onRenderProcessGone(view: WebView?, detail: android.webkit.RenderProcessGoneDetail?): Boolean {
                try {
                    (view?.parent as? android.view.ViewGroup)?.removeView(view)
                    view?.destroy()
                } catch (_: Exception) {}
                return true
            }
        }"""
content = content.replace(old_client, new_client)

with open("app/src/main/java/com/example/util/WorksheetPrintManager.kt", "w") as f:
    f.write(content)
