package com.example.will.goactivity.Controllers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.will.goactivity.R
import com.example.will.goactivity.Utilities.STATE_COUNT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FlowActivity() {
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        showStep {  }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goBtn.setOnClickListener {
//            Log.d(TAG, "count：${++count}")
            var secondIntent = Intent(this, SecondActivity::class.java)
            startActivity(secondIntent)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        showStep {  }
        // 儲存狀態
        savedInstanceState.putInt(STATE_COUNT, count)

        // 請始終呼叫超級類別實作，以便預設實作可儲存檢視階層的狀態
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        showStep {  }
        // 請始終呼叫超級類別實作，以便預設實作可還原檢視階層的狀態。
        super.onRestoreInstanceState(savedInstanceState)

        // 將儲存的狀態取回
        count = savedInstanceState.getInt(STATE_COUNT)
    }

    var lastTime: Long = 0

    override fun finish() {
        val currentTime = System.currentTimeMillis()

        if(currentTime - lastTime > 3 * 1000) {
            lastTime = currentTime
            Toast.makeText(this, "再按一下離開，我們明天見！", Toast.LENGTH_SHORT).show()
        } else {
            super.finish()
        }
    }
}
