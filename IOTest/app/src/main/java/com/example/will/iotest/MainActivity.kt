package com.example.will.iotest

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.media.audiofx.EnvironmentalReverb
import android.net.ConnectivityManager
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var scroll: TextView
    private lateinit var sb: StringBuilder
    private lateinit var dbHelper: DBHelper
    private lateinit var database: SQLiteDatabase

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        scroll = findViewById(R.id.scroll)
//        sp = getSharedPreferences("config", Context.MODE_PRIVATE)
//        editor = sp.edit()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
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

    private lateinit var sdRoot: File
    private lateinit var appRoot: File
    private lateinit var dcimRoot: File

    private fun init() {
        dcimRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        Log.v("WillLog", dcimRoot.absolutePath)

        sdRoot = Environment.getExternalStorageDirectory()
        Log.v("WillLog", sdRoot.absolutePath)

        val downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Log.v("WillLog", downloadPath.absolutePath)

        val files = downloadPath.listFiles()

        for (file in files) {
            Log.v("WillLog", "${file.name}:${if (file.isFile) "File" else "Dir"}")
        }

        appRoot = File(sdRoot, "Android/data/${packageName}/")

        if (!appRoot.exists()) {
            Log.v("WillLog", "建立資料夾...")
            appRoot.mkdirs()
        }

        //DB
        dbHelper = DBHelper(this, "will", null, 2018120701)
        database = dbHelper.readableDatabase
    }

    fun testSD1(view: View) {
        val file1 = File(sdRoot, "sakura.txt")

        try {
            val writer = FileWriter(file1)
            writer.write("Hello, Sakura")
            writer.flush()
            writer.close()
            Toast.makeText(this, "Save OK1", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.v("WillLog", e.toString())
        }
    }

    fun testSD2(view: View) {
        val file1 = File(appRoot, "sakura.txt")

        try {
            val writer = FileWriter(file1)
            writer.write("Hello, Sakura")
            writer.flush()
            writer.close()
            Toast.makeText(this, "Save OK2", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.v("WillLog", e.toString())
        }
    }

    fun testDBC(view: View) {
        val values = ContentValues()
        values.put("CustName", "Will")
        values.put("Tel", "0900-000000")
        values.put("Birthday", "2018-12-04")
        database.insert("sakura", null, values)
    }

    fun testDBOC(view: View) {
        val values = ContentValues()
        values.put("ProdName", "A_${Math.random() * 10}")
        values.put("QTY", "A_${Math.random() * 10}")

        database.insert("skOrder", null, values)
        testDBR(view)
    }

    fun testDBR(view: View) {
        //val cursor = database.query("sakura", null, null, null, null, null, null)
        val cursor = database.query(
            "sakura",
            arrayOf("Id", "CustName AS Name", "Tel"),
            "Id > ?",
            arrayOf("1"),
            null,
            null,
            "CustName desc LIMIT 2,4")

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex("Id"))
            val custName = cursor.getString(cursor.getColumnIndex("Name"))
            val tel = cursor.getString(cursor.getColumnIndex("Tel"))
//            val birthday = cursor.getString(cursor.getColumnIndex("Birthday"))
            Log.v("WillLog", "$id, $custName, $tel")
        }
    }

    fun testDBD(view: View) {
        database.delete("sakura", "Id = ? AND CustName = ?", arrayOf("3", "Will"))
        testDBR(view)
    }

    fun testDBU(view: View) {
        val values = ContentValues()
        values.put("CustName", "Tom")
        values.put("Tel", "0900-123123")
        database.update("sakura", values, "Id = ?", arrayOf("5"))
        testDBR(view)
    }

    fun testDBJOIN(view: View) {
        val cursor = database.query(
            "sakura, skOrder",
            null,
            "sakura.ID = skOrder.SID",
            null,
            null,
            null,
            null)

        while (cursor.moveToNext()) {
            var str: String = ""
            
            for(name in cursor.columnNames) {
                str += "$name: ${cursor.getString(cursor.getColumnIndex(name))}"
            }

            Log.v("WillLog", str)
        }

        testDBR(view)
    }

    fun testI1(view: View) {
        sb = StringBuilder()
        Thread(Runnable {
            try {
                val url = URL("https://www.sakura.com.tw/")
                val httpRequest = url.openConnection() as HttpURLConnection
                httpRequest.connect()
                val reader = BufferedReader(InputStreamReader(httpRequest.inputStream))
                var line: String?
                var count = 0

                do {
                    line = reader.readLine()
                    sb.append(line)
                    Log.v("LogWill", "${++count}：$line")
                } while (line != null)

                reader.close()

                this@MainActivity.runOnUiThread {
                    scroll.text = sb.toString()
                }

            } catch (e: Exception) {
                Log.v("LogWill", e.toString())
            }
        }).start()
    }

    private fun parseJSON(json: String) {
        try {
            val root = JSONArray(json)

            for (i in 0..root.length()) {
                val obj = root.getJSONObject(i)
                val name = obj.getString("Name")
                val tel = obj.getString("Tel")
                Log.v("LogWill", "$name, $tel")
            }
        } catch (e: Exception) {

        }
    }

    fun test1(view: View) {
        editor.putString("username", "Will")
        editor.putString("password", "123456")
        editor.putBoolean("sound", false)
        editor.commit()
        Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show()
    }

    fun test2(view: View) {
        val sound = sp.getBoolean("sound", true)
        val username = sp.getString("username", "guest")
        val password = sp.getString("password", "123")
    }

    fun test3(view: View) {
        editor.clear()
        editor.commit()
    }

    fun test4(view: View) {
        try {
            val fileOut = openFileOutput("data.txt", Context.MODE_APPEND)
            fileOut.write("Hello\n".toByteArray())
            fileOut.flush()
            fileOut.close()
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.v("will", e.toString())
        }
    }

    fun test5(view: View) {
        try {
            val fileOut = openFileInput("data.txt")
            val ipr = InputStreamReader(fileOut)
            val bfr = BufferedReader(ipr)
            var line: String?

            do {
                line = bfr.readLine()
                Log.v("will", line)
            } while (line != null)

            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.v("will", e.toString())
        }
    }
}
