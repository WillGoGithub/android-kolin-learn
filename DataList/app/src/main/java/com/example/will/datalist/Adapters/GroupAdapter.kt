package com.example.will.datalist.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.will.datalist.Models.FoodGroup
import com.example.will.datalist.R

class GroupAdapter(val context: Context, val groups: List<FoodGroup>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 首先取出剛剛設計的個別項目 layout
        val groupView: View
        val holder: ViewHolder

        if (convertView == null) {
            groupView = LayoutInflater
                        .from(context)
                        .inflate(R.layout.group_list_item, parent, false)
            holder = ViewHolder()
            // 取得項目樣式的元件
            holder.groupImage = groupView.findViewById(R.id.itemImage)
            holder.groupText = groupView.findViewById(R.id.groupName)
            groupView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            groupView = convertView
        }

        // 取得資料
        val group = getItem(position)

        // 變更圖片
        val imgRid = context.resources
                    .getIdentifier(group.image, "drawable", context.packageName)
        holder.groupImage?.setImageResource(imgRid)

        // 變更文字
        holder.groupText?.text = group.name

        return groupView
    }

    override fun getItem(position: Int): FoodGroup {
        return groups[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return groups.count()
    }

    private class ViewHolder{
        var groupImage: ImageView? = null
        var groupText: TextView? = null
    }
}