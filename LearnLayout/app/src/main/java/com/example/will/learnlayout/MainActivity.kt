package com.example.will.learnlayout

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.BitmapDrawable

data class IsData (val name: String, var age: Int) {}
class NonData (val name: String, var age: Int) {}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        for (i in 1..4) {
            val resID = resources.getIdentifier("photo$i", "id", packageName)
            val view = findViewById<ImageView>(resID)
            roundedImage(view)
        }
    }

    fun roundedImage(imgView: ImageView) {
        imgView.invalidate()
        val drawable = (imgView.drawable as BitmapDrawable).bitmap
        var rounded = RoundedBitmapDrawableFactory.create(resources, drawable)
        // 圓角
        rounded.cornerRadius = 50f
        // 圓形
        //rounded.isCircular = true
        imgView.setImageDrawable(rounded)
    }
}
