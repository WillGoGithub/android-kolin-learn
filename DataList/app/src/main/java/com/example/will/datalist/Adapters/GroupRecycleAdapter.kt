package com.example.will.datalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.will.datalist.Models.FoodGroup
import com.example.will.datalist.R

class GroupRecycleAdapter(val context: Context, val groups: List<FoodGroup>, val itemClickEvent: (FoodGroup) -> Unit)
    : RecyclerView.Adapter<GroupRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.group_list_item, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return groups.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindGroup(groups[position], context)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val groupImage: ImageView = itemView.findViewById(R.id.groupImage)
        val groupName: TextView = itemView.findViewById(R.id.groupName)

        fun bindGroup(group: FoodGroup, context: Context) {
            val resourceId = context.resources.getIdentifier(group.image, "drawable", context.packageName)
            groupImage.setImageResource(resourceId)
            groupName.text = group.name
            itemView.setOnClickListener { itemClickEvent(group) }
        }
    }
}
