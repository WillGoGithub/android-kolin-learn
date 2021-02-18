package com.example.will.webview2

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var textView: TextView
    private lateinit var handler: UIHandler
    private lateinit var progressDialog: ProgressDialog
    private var mainName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = UIHandler()
        webView = findViewById(R.id.webview)
        textView = findViewById(R.id.textview)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        initView()
    }

    private fun initView() {
        webView.settings.javaScriptEnabled = true

        webView.addJavascriptInterface(MyJS(), "sakura")
        webView.webViewClient = MyWebViewClient()
        webView.webChromeClient = MyChromeClient()
//        webView.loadUrl("file:///android_asset/web1.html")
        webView.loadUrl("https://www.sakura.com.tw/")
    }

    inner class MyJS {
        @JavascriptInterface
        fun method1(myName: String) {
            Log.v("WillLog", "OK $myName")
            mainName = myName
            handler.sendEmptyMessage(0)
        }
    }

    private inner class MyWebViewClient: WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressDialog.show()
            Log.v("WillLog", "onPageStarted: $url")

            if (url == "https://www.sakura.com.tw/customer") {
                webView.loadUrl("https://crmreport.sakura.com.tw/")
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressDialog.dismiss()
            Log.v("WillLog", "onPageFinished: $url")
        }

    }

    private inner class MyChromeClient: WebChromeClient() {

    }

    private inner class UIHandler: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            textView.text = mainName
        }
    }
}
