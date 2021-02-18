package com.example.will.mytimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var timeHandler: Handler
    private lateinit var timeRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeHandler = Handler()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun start(view: View) {
        var i = 0

        timeRunnable = Runnable {
            textView.text = "${i++}"
            timeHandler.postDelayed(timeRunnable, 1000)
        }

        timeHandler.postDelayed(timeRunnable, 0)
    }

    fun stop(view: View) {
        timeHandler.removeCallbacks(timeRunnable)
    }

}
