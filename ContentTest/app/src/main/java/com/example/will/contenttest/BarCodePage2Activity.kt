package com.example.will.contenttest

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.SparseArray
import com.google.android.gms.vision.barcode.Barcode
import info.androidhive.barcode.BarcodeReader

class BarCodePage2Activity : AppCompatActivity(), BarcodeReader.BarcodeReaderListener {
    private lateinit var fmgr: FragmentManager
    private lateinit var barcodeReader: BarcodeReader

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScannedMultiple(barcodes: MutableList<Barcode>?) {
        if (barcodes != null) {
            for (barcode in barcodes.iterator()) {
                Log.v("WillLog", barcode?.rawValue)
            }
        }
    }

    override fun onCameraPermissionDenied() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScanned(barcode: Barcode?) {
        Log.v("WillLog", barcode.toString())
        Log.v("WillLog", barcode?.rawValue)
        Log.v("WillLog", barcode?.displayValue)
    }

    override fun onScanError(errorMessage: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code_page2)


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA
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
        fmgr = supportFragmentManager
        barcodeReader = fmgr.findFragmentById(R.id.page2f1) as BarcodeReader
    }
}
