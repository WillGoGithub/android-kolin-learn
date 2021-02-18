package com.example.will.examples

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import android.R.string.ok


class MainActivity : AppCompatActivity() {
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arraySize = resources.getStringArray(R.array.options).size

        var arrOpts = BooleanArray(arraySize) { true }

        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder
            .setTitle("標題")
            .setMultiChoiceItems(R.array.options, arrOpts) { _, which, isChecked ->
                arrOpts[which] = isChecked
                Log.i("SHOW_CONFIG", "${arrOpts.toList()}")
            }
            .setPositiveButton("確定") { dialog, which ->
                showWitch(dialog, which)
            }
            .setNegativeButton("取消") { dialog, which ->
                showWitch(dialog, which)
            }

        /*                    .setMessage("內容")

            alertBuilder.setPositiveButton("確定") { dialog, which ->
                showWitch(dialog, which) // -1
            }

            alertBuilder.setNegativeButton("拒絕") { dialog, which ->
                showWitch(dialog, which) // -2
            }

            alertBuilder.setNeutralButton("稍後決定") { dialog, which ->
                showWitch(dialog, which) // -3
            }*/


        alertDialog = alertBuilder.create()
        alertBtn.setOnClickListener { alertDialog.show() }
    }

    private fun showWitch(dialog: DialogInterface?, which: Int) {
        Log.i("SHOW_CONFIG", which.toString())
    }

    private fun showConfig() {
        Log.i("SHOW_CONFIG", "screenWidthDp: ${resources.configuration.screenWidthDp}")
        Log.i("SHOW_CONFIG", "screenHeightDp: ${resources.configuration.screenHeightDp}")
        Log.i("SHOW_CONFIG", "densityDpi: ${resources.configuration.densityDpi}")
        Log.i("SHOW_CONFIG", "fontScale: ${resources.configuration.fontScale}")

        Log.i("SHOW_CONFIG", "orientation: ${resources.configuration.orientation}")
        Log.i("SHOW_CONFIG", "ORIENTATION_PORTRAIT: ${Configuration.ORIENTATION_PORTRAIT}")
        Log.i("SHOW_CONFIG", "ORIENTATION_LANDSCAPE: ${Configuration.ORIENTATION_LANDSCAPE}")

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.i("SHOW_CONFIG", "直")
                // TODO
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                Log.i("SHOW_CONFIG", "橫")
                // TODO
            }
        }
    }
}
