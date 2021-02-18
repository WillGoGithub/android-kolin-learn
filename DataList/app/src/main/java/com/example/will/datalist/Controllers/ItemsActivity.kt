package com.example.will.datalist.Controllers

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.will.datalist.Adapters.GroupRecycleAdapter
import com.example.will.datalist.Adapters.ItemRecycleAdapter
import com.example.will.datalist.Models.FoodGroup
import com.example.will.datalist.Models.FoodItem
import com.example.will.datalist.R
import com.example.will.datalist.Services.DataService
import com.example.will.datalist.Utilities.EXTRA_GROUP
import kotlinx.android.synthetic.main.activity_main.*

class ItemsActivity : AppCompatActivity() {

    lateinit var adapter: ItemRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val group = intent.getParcelableExtra<FoodGroup>(EXTRA_GROUP)
        titleText.text = group.name
        // 產生資料清單
        generateListView(group.list)
    }

    private fun generateListView(list: List<FoodItem>?) {
        if (list != null) {
            adapter = ItemRecycleAdapter(this, list)
            groupRecyclerView.adapter = adapter

            var spanCount = 2

            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE ||
                resources.configuration.screenWidthDp > 720) {
                spanCount = 3
            }

            groupRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
        }
    }
}
