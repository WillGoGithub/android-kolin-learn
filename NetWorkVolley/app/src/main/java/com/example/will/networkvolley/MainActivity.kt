package com.example.will.networkvolley

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var lotteryTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        lotteryTxt = findViewById(R.id.lotteryTxt)
    }

    fun btn1(view: View) {
        val request = StringRequest(Request.Method.GET,
            "https://www.bradchao.com/v2/apptest/getTest1.php?account=brad&passwd=1234",
            Response.Listener {
                Log.v("WillLog", it)
                parseJSON(it)
            },
            null)

        queue.add(request)
    }

    fun parseJSON(json: String) {
        try {
            val root = JSONObject(json)
            val result = root.getString("result")
            val account = root.getString("account")
            val passwd = root.getString("passwd")
            val lottery = root.getString("lottery")

            lotteryTxt.text = lottery
        } catch (e: Exception) {
            Log.v("WillLog", e.toString())
        }
    }

    fun btn2(view: View) {
        var request = object : StringRequest(
            Request.Method.POST,
            "https://www.bradchao.com/v2/apptest/postTest1.php",
            Response.Listener {
                Log.v("WillLog", it)
                parseJSON(it)
            },
            null
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("account", "brad")
                params.put("passwd", "1234")
                return params
            }
        }

        queue.add(request)
    }

    fun parseJSONDecode(json: String) {
        try {
            val root = JSONObject(json)
            val auther = root.getString("Auther")
            lotteryTxt.text = auther
        } catch (e: Exception) {
            Log.v("WillLog", e.toString())
        }
    }

    fun btn3(view: View) {
        var request = object : StringRequest(
            Request.Method.GET,
            "https://www1.sakura.com.tw/sakura/rstcom.nsf/org.xsp/per?u=987658",
            Response.Listener {
                Log.v("WillLog", it)
                parseJSONDecode(it)
            },
            null
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val data = "brad:bradpass!"
                val encode = Base64.encodeToString(data.toByteArray(), Base64.NO_WRAP)
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Basic $encode")
                headers.put("Content-type", "application/json; charset=UTF-8")
                return headers
            }
        }

        queue.add(request)
    }
}
