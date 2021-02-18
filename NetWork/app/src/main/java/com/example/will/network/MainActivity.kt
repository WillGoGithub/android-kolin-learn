package com.example.will.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var cmgr: ConnectivityManager
    private lateinit var wmgr: WifiManager
    private lateinit var myReceiver: MyReceiver
    private lateinit var imgView: ImageView

    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ConnectivityManager.CONNECTIVITY_ACTION -> {
                    Log.v("WillLog", "${isConnectNet()}")
                    Log.v("WillLog", "${wmgr.isWifiEnabled}")
                }
                "IMG" -> {
                    showImg(intent)
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        unregisterReceiver(myReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myReceiver = MyReceiver()
        imgView = findViewById(R.id.img)
        cmgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        wmgr = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction("IMG")
        registerReceiver(myReceiver, filter)
    }

    private fun isConnectNet(): Boolean {
        val info = cmgr.activeNetworkInfo
        return (info != null && info.isConnected)
    }

    fun btnA(view: View) {
        val intent = Intent(this, MyService::class.java)
        startService(intent)
        stopService(intent)
    }

    fun btnB(view: View) {
        Log.v("WillLog", "btnB")
        val intent = Intent(this, MyService2::class.java)
        intent.putExtra("CMD", MyService2.CMD_GOTO_SK)
        this.startService(intent)
    }

    fun btnC(view: View) {
        val intent = Intent(this, MyService2::class.java)
        intent.putExtra("CMD", MyService2.CMD_GOTO_IMG)
        this.startService(intent)
    }

    fun showImg(intent: Intent?) {
        val bmp = intent?.getParcelableExtra<Bitmap>("img")
        imgView.setImageBitmap(bmp)
    }
}
