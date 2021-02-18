package com.example.will.mysignature

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var myView: MyView
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myView = findViewById(R.id.myView)
        seekBar = findViewById(R.id.seekBar)
        myView.setLineWidth(seekBar.progress)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    myView.setLineWidth(i)
                }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                Toast.makeText(applicationContext,"${seekBar.progress}",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                Toast.makeText(applicationContext,"${seekBar.progress}",Toast.LENGTH_SHORT).show()
            }
        })

        seekBar_A.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                textView_A.text = "${textView_A.tag}$i"
                setColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })

        seekBar_R.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                textView_R.text = "${textView_R.tag}$i"
                setColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })

        seekBar_G.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                textView_G.text = "${textView_G.tag}$i"
                setColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })

        seekBar_B.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                textView_B.text = "${textView_B.tag}$i"
                setColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })
    }

    fun clear(view: View) {
        myView.clear()
    }

    fun undo(view: View) {
        myView.undo()
    }

    fun redo(view: View) {
        myView.redo()
    }

    fun setColor() {
        val A = seekBar_A.progress
        val R = seekBar_R.progress
        val G = seekBar_G.progress
        val B = seekBar_B.progress
        myView.setColor(Color.argb(A, R, G ,B))
    }
}
