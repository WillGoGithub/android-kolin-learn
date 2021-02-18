package com.example.will.contenttest

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var cr: ContentResolver
    private lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgView = findViewById(R.id.imgView)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CONTACTS
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
        cr = contentResolver
    }

    fun BtnContact(view: View) {
        val cursor = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )

        Log.v("WillLog", "count = ${cursor.count}")
        Log.v("WillLog", "fields = ${(cursor.columnNames).joinToString()}")
        Log.v("WillLog", "${ContactsContract.Contacts.DISPLAY_NAME}")
        Log.v("WillLog", "${ContactsContract.CommonDataKinds.Phone.DATA1}")

        while (cursor.moveToNext()) {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val name = cursor?.getString(nameIndex)

            Log.v("WillLog", "nameIndex = $nameIndex, name = $name")

            val telIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1)
            val tel = cursor?.getString(telIndex)

            Log.v("WillLog", "telIndex = $telIndex, tel = $tel")
        }
    }

    fun BtnCamera(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            afterCamera(data)
        }
    }

    private fun afterCamera(data: Intent?) {
        Log.v("WillBrad", "OK")

        val bitmap = data?.extras?.get("data") as Bitmap
        imgView.setImageBitmap(bitmap)
    }
}
