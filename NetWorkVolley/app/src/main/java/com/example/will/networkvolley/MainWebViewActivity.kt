package com.example.will.networkvolley

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.sip.SipSession
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.jar.Manifest

class MainWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var lmgr: LocationManager
    private lateinit var myListener: MyListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
    }

    private fun init() {
        Log.v("WillLog", "init")
        myListener = MyListener()
        lmgr = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, myListener)
        webView = findViewById(R.id.webView)
        initWebView()
    }

    override fun onStart() {
        super.onStart()
        Log.v("WillLog", "onStart")

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                0)
        } else {
            init()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.v("WillLog", "onStop")
        lmgr.removeUpdates(myListener)
    }

    inner class MyListener: LocationListener {
        override fun onLocationChanged(location: Location?) {
            Log.v("WillLog", "${location?.latitude}, ${location?.longitude}")
            webView.loadUrl("javascript:gotoKD(${location?.latitude}, ${location?.longitude})")
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.size > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init()
        } else {
            finish()
        }
    }

    private fun initWebView() {
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
//        webView.loadUrl("https://www.sakura.com.tw")
        webView.loadUrl("file:///android_asset/map.html")
    }

    fun webBtn1(view: View) {
        val lat = 24.001420
        val lng = 121.606043
        webView.loadUrl("javascript:gotoKD($lat, $lng)")
    }

    fun webBtn2(view: View) {

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
