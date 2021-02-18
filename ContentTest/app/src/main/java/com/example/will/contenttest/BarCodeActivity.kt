package com.example.will.contenttest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View

class BarCodeActivity : AppCompatActivity() {
    private lateinit var F1: BlankFragment1
    private lateinit var F2: BlankFragment2
    private lateinit var fmgr: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code)

        F1 = BlankFragment1()
        F2 = BlankFragment2()
        fmgr = supportFragmentManager
        val transaction = fmgr.beginTransaction()
        transaction.add(R.id.container, F1)
        transaction.commit()
    }

    fun btnF1(view:View) {
        val transaction = fmgr.beginTransaction()
        transaction.replace(R.id.container, F1)
        transaction.commit()
    }

    fun btnF2(view: View) {
        val transaction = fmgr.beginTransaction()
        transaction.replace(R.id.container, F2)
        transaction.commit()
    }

    fun goPage2Btn(view: View) {
        val intent = Intent(this, BarCodePage2Activity::class.java)
        startActivity(intent)
    }
}
