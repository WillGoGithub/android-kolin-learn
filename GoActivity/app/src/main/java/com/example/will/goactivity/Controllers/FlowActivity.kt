package com.example.will.goactivity.Controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.will.goactivity.R

open class FlowActivity : AppCompatActivity() {

    val TAG = "Flow"

    override fun onCreate(savedInstanceState: Bundle?) {
        showStep { }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
    }

    override fun onStart() {
        showStep { }
        super.onStart()
    }

    override fun onResume() {
        showStep { }
        super.onResume()
    }

    override fun onRestart() {
        showStep { }
        super.onRestart()
    }

    override fun onPause() {
        showStep { }
        super.onPause()
    }

    override fun onStop() {
        showStep { }
        super.onStop()
    }

    override fun onDestroy() {
        showStep { }
        super.onDestroy()
    }

    fun showStep(o: () -> Unit) {
        Log.d(TAG, "頁面：${javaClass.simpleName}，流程：${o.javaClass.enclosingMethod!!.name}")
    }
}
