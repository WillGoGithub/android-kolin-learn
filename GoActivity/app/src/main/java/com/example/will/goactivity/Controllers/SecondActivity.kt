package com.example.will.goactivity.Controllers

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.will.goactivity.Models.Schedule
import com.example.will.goactivity.R
import com.example.will.goactivity.Utilities.EXTRA_AURORA_DATA
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : FlowActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        backBtn.setOnClickListener { backBtnOnClick() }
        nextBtn.setOnClickListener { nextBtnOnClick() }
    }

    fun backBtnOnClick() {
        var mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

    private fun nextBtnOnClick() {
        var schedule = Schedule(swtArctic.isChecked, swtAntarctic.isChecked, swtFinland.isChecked)

        if (schedule.hasAnyChecked) {
            var thirdActivity = Intent(this, ThirdActivity::class.java)
            thirdActivity.putExtra(EXTRA_AURORA_DATA, schedule)
            startActivity(thirdActivity)
        } else {
            Toast.makeText(this, "請至少選擇一個項目", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed() 拒絕返回鍵
        val builder = AlertDialog.Builder(this)

        builder.setTitle("訊息")
        builder.setMessage("真的要離開嗎？")
        builder.setPositiveButton("是") { _,_ ->
            finish()
        }
        builder.setNegativeButton("否", null)
        builder.setCancelable(false)
        builder.create().show()
    }
}