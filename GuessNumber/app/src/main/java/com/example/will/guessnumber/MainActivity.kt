package com.example.will.guessnumber

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var answer = LinkedList<Int>()
    private var input = arrayOfNulls<TextView>(4)
    private var inputRes = intArrayOf(R.id.main_input1, R.id.main_input2, R.id.main_input3, R.id.main_input4)
    private var inputValue = LinkedList<Int>()   // 輸入數值陣列
    private var inputPoint: Int = 0     // 輸入指標位置 0 - 3
    private var btnNumber = arrayOfNulls<View>(10)
    private var numberRes = intArrayOf(
        R.id.main_btn_0,
        R.id.main_btn_1,
        R.id.main_btn_2,
        R.id.main_btn_3,
        R.id.main_btn_4,
        R.id.main_btn_5,
        R.id.main_btn_6,
        R.id.main_btn_7,
        R.id.main_btn_8,
        R.id.main_btn_9
    )

    private var listView: ListView? = null
    private var adapter: SimpleAdapter? = null
    private var from = arrayOf("order", "guess", "result")
//    private val to = intArrayOf(R.id.item_order, R.id.item_guess, R.id.item_result)
    private var hist: LinkedList<HashMap<String, String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initGame()
    }


    // 初始化畫面
    private fun initView() {
        for (i in inputRes.indices) {
            input[i] = findViewById(inputRes[i])
        }

        for (i in numberRes.indices) {
            btnNumber[i] = findViewById(numberRes[i])
        }
    }

    // 初始化一局遊戲
    private fun initGame() {
        answer = createAnswer()
        clear(null)
    }

    // 輸入數字鍵
    fun inputNumber(view: View) {
        view.tag
        if (inputPoint == 4) return     // 此時只能 send or back or clear

        // 比對輸入鍵
        for (i in btnNumber.indices) {
            if (view === btnNumber[i]) {
                // 輸入 i 鍵
                inputValue[inputPoint] = i
                input[inputPoint]?.text = i.toString()
                inputPoint++
                btnNumber[i]?.isEnabled = false
                break
            }
        }
    }


    // 輸入清除鍵
    fun clear(view: View?) {
        inputPoint = 0
        inputValue.clear()
        for (i in 0..3) {
            inputValue.add(-1)
        }
        for (i in input.indices) {
            input[i]?.setText("")
        }
        for (i in btnNumber.indices) {
            btnNumber[i]?.setEnabled(true)
        }
    }

    // 產生謎底
    private fun createAnswer(): LinkedList<Int> {
        val ret = LinkedList<Int>()
        val nums = HashSet<Int>()
        while (nums.size < 4) {
            nums.add((Math.random() * 10).toInt())
        }
        for (i in nums) {
            ret.add(i)
        }
        Collections.shuffle(ret)
        return ret
    }

    // 輸入退位鍵
    fun back(view: View) {
    }


    // 按下送出
    fun send(view: View) {
        if (inputPoint != 4) return

        var a: Int
        var b: Int
        b = 0
        a = b
        var guess = ""
        for (i in inputValue.indices) {
            guess += inputValue[i]
            if (inputValue[i] == answer[i]) {
                a++
            } else if (answer.contains(inputValue[i])) {
                b++
            }
        }
        Log.d("brad", a.toString() + "A" + b + "B")
        clear(null)

        val row = HashMap<String, String>()
        row[from[0]] = "" + (hist?.size?.plus(1))
        row[from[1]] = guess
        row[from[2]] = a.toString() + "A" + b + "B"
        hist?.add(row)
        adapter?.notifyDataSetChanged()
        listView?.smoothScrollToPosition(hist?.size!!.plus(-1))

        if (a == 4) {
            // winner
            displayResult(true)
        } else if (hist?.size == 10) {
            // loser
            displayResult(false)
        }
    }

    // 顯示輸贏結果
    private fun displayResult(isWinner: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("遊戲結果")

        val ansString = StringBuffer()
        for (i in answer.indices) ansString.append(answer[i])

        builder.setMessage(if (isWinner) "完全正確" else "挑戰失敗\n答案:$ansString")
        builder.setPositiveButton("開新局") { dialogInterface, i -> replay(null) }
        builder.create().show()
    }

    // 重玩
    fun replay(view: View?) {
    }
}
