package com.example.lightbrowser

import android.webkit.WebView

class ExtensionManager {
    fun injectExtensions(webView: WebView, extensions: List<Extension>) {
        val enabled = extensions.filter { it.enabled }
        if (enabled.isEmpty()) return

        val script = enabled.joinToString("\n") { it.script }

        webView.evaluateJavascript(
            """
            (() => {
                try {
                    $script
                } catch (error) {
                    console.error("Extension error: " + error.toString());
                }
            })();
            """.trimIndent(),
            null
        )
    }
}