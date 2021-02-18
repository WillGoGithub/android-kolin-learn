package com.example.will.datalist.Adapters

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.will.datalist.Models.FoodGroup
import com.example.will.datalist.Models.FoodItem
import com.example.will.datalist.R

class ItemRecycleAdapter(val context: Context, val items: List<FoodItem>)
    : RecyclerView.Adapter<ItemRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_list_item, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindGroup(items[position], context)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val itemName: TextView = itemView.findViewById(R.id.itemName)

        fun bindGroup(item: FoodItem, context: Context) {
            val resourceId = context.resources.getIdentifier(item.image, "drawable", context.packageName)
            itemImage.setImageResource(resourceId)
            itemName.text = item.name
        }
    }
}
