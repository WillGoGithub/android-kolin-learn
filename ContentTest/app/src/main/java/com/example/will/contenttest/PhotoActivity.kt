package com.example.will.contenttest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.io.File

class PhotoActivity : AppCompatActivity() {
    private lateinit var sdroot: File
    private lateinit var picfile: File
    private lateinit var imgView: ImageView
    private lateinit var qrTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                123
            )
        } else {
            init()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        init()
    }

    private fun init() {
        sdroot = Environment.getExternalStorageDirectory()
        picfile = File(sdroot, "test.png")
        imgView = findViewById(R.id.imgView)
        qrTextView = findViewById(R.id.QRText)
    }

    fun Btn1(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoUri = FileProvider.getUriForFile(this, "$packageName.provider", picfile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, 2)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            val bmp = BitmapFactory.decodeFile(picfile.absolutePath)
            imgView.setImageBitmap(bmp)
        } else if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            qrTextView.text = data?.getStringExtra("code")
        }
    }

    fun BtnQR(view: View) {
        val intent = Intent(this, ScannerActivity::class.java)
        startActivity(intent)
    }

    fun BtnBar(view: View) {
        val intent = Intent(this, BarCodeActivity::class.java)
        startActivity(intent)
    }
}
