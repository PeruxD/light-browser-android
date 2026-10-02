package com.example.lightbrowser

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.lightbrowser.databinding.ActivityBrowserBinding

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding
    private lateinit var prefs: BrowserPreferences
    private lateinit var extensionManager: ExtensionManager

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = BrowserPreferences(this)
        extensionManager = ExtensionManager()

        val webView = binding.browserWebView
        val settings: WebSettings = webView.settings

        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.loadsImagesAutomatically = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.userAgentString =
            settings.userAgentString.replace("wv", "") + " LightBrowser/1.0"

        webView.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url != null) {
                    binding.browserUrlInput.setText(url)
                    injectExtensions(webView)
                }
            }
        }

        webView.webChromeClient = android.webkit.WebChromeClient()

        val initialUrl = intent.getStringExtra("url") ?: "https://www.google.com"

        binding.browserUrlInput.setText(initialUrl)
        binding.loadUrlButton.setOnClickListener {
            loadUrl(webView, binding.browserUrlInput.text.toString())
        }

        binding.backButton.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
        }

        binding.forwardButton.setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        binding.reloadButton.setOnClickListener {
            webView.reload()
        }

        binding.extensionsButton.setOnClickListener {
            // Future: Open extensions manager
        }

        loadUrl(webView, initialUrl)
    }

    private fun loadUrl(webView: WebView, rawUrl: String) {
        val url = if (rawUrl.startsWith("http://") || rawUrl.startsWith("https://")) {
            rawUrl
        } else {
            "https://$rawUrl"
        }
        webView.loadUrl(url)
    }

    private fun injectExtensions(webView: WebView) {
        val extensions = prefs.loadExtensions()
        extensionManager.injectExtensions(webView, extensions)
    }
}