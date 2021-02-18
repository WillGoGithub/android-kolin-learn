package com.example.will.contenttest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

class ScannerActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {
    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        Log.v("WillLog", text)
        val intent = Intent()
        intent.putExtra("code", text)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private lateinit var scanner: QRCodeReaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                0
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
        scanner = findViewById(R.id.scanView)
        scanner.setOnQRCodeReadListener(this)
        scanner.setQRDecodingEnabled(true)
        scanner.setBackCamera()
        scanner.setAutofocusInterval(2000)
    }

    override fun onStart() {
        super.onStart()
        scanner.startCamera()
    }

    override fun onStop() {
        super.onStop()
        scanner.stopCamera()
    }
}
