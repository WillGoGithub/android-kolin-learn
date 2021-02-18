package com.example.will.datalist.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.will.datalist.Adapters.GroupRecycleAdapter
import com.example.will.datalist.R
import com.example.will.datalist.Services.DataService
import com.example.will.datalist.Utilities.EXTRA_GROUP
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: GroupRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 產生資料清單
        generateListView()
    }

    fun generateListView() {
        adapter = GroupRecycleAdapter(this, DataService.groups) {
            var itemIntent = Intent(this, ItemsActivity::class.java)
            itemIntent.putExtra(EXTRA_GROUP, it)
            startActivity(itemIntent)
        }
        groupRecyclerView.adapter = adapter
        groupRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
