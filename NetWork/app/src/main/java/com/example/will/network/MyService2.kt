package com.example.will.network

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.IBinder
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class MyService2 : Service() {
    companion object {
        val CMD_GOTO_SK = 1
        val CMD_GOTO_IMG = 2
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("WillLog", "onStartCommand")
        when(intent?.getIntExtra("CMD", 0)) {
            CMD_GOTO_SK -> gotoSakura()
            CMD_GOTO_IMG -> gotoSakuraImg()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    fun gotoSakura() {
        Thread {
            Log.v("WillLog", "gotoSakura")
            try {
                val url = URL("https://www.sakura.com.tw/")
                val conn = url.openConnection()
                conn.connect()

                val reader = BufferedReader(InputStreamReader(conn.getInputStream()))
                var line: String

                do {
                    line = reader.readLine()
                    Log.v("WillLog", line)
                } while (line != null)
                reader.close()
            } catch (e: Exception) {
                Log.v("WillLog", e.message)
            }
        }.start()
    }

    fun gotoSakuraImg() {
        Thread {
            Log.v("WillLog", "gotoSakuraImg")
            try {
                val url = URL("https://www.sakura.com.tw/openkitchen/images/layout/bg3.png")
                val conn = url.openConnection()
                conn.connect()
                val intent = Intent("IMG")
                intent.putExtra("img", BitmapFactory.decodeStream(conn.getInputStream()))
                sendBroadcast(intent)
            } catch (e: Exception) {
                Log.v("WillLog", e.message)
            }
        }.start()
    }
}