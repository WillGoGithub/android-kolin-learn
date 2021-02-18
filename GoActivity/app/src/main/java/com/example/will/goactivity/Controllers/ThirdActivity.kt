package com.example.will.goactivity.Controllers

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.will.goactivity.Models.Schedule
import com.example.will.goactivity.R
import com.example.will.goactivity.Utilities.EXTRA_AURORA_DATA
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.coroutines.*

class ThirdActivity : FlowActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        var schedule: Schedule = intent.getParcelableExtra(EXTRA_AURORA_DATA)
        showScheduling(schedule.getCheckedValues())
    }

    private fun showScheduling(results: Array<String>) {
        var index = 0
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                if (index < results.count()) {
                    textView.text = results[index++]
                    handler.postDelayed(this, 2500)
                } else {
                    textView.text = "完成"
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

//        var list = intent.getBundleExtra(EXTRA_AURORA_LIST).getSerializable(EXTRA_AURORA_LIST) as HashMap<String, Boolean>
//        var results = list.filter { it.value }.keys.toTypedArray()
}
