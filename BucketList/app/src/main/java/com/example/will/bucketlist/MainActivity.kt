package com.example.will.bucketlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val bucketList = mutableListOf("登上富士山", "去“嵐山岩田山猴子公園”",
            "搭乘新幹線", "到秋葉原逛街", "體驗小鋼珠", "品嘗拉麵", "用大頭貼機拍照",
            "賞櫻花", "觀賞歌舞伎表演", "參觀古城堡", "去嚴島神社", "穿和服", "原宿竹下通購物")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener { addBtnOnClick() }
        pickBtn.setOnClickListener { pickBtnOnClick() }
    }

    private fun addBtnOnClick() {
        val newWish = wishTxt.text.toString()
        bucketList.add(newWish)
        wishTxt.text.clear()
    }

    private fun pickBtnOnClick() {
        val count = bucketList.count()

        if (count > 0) {
            val random = (Math.random() * count).toInt()
            toDoTxt.text = bucketList[random]
            bucketList.removeAt(random)
        } else {
            toDoTxt.text = getString(R.string.no_more_plan_txt)
        }

        // TODO Wait for a decision
    }
}
