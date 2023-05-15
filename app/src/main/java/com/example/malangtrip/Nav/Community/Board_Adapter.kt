package com.example.malangtrip.Nav.Community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.malangtrip.R

class Board_Adapter(val writer_info :MutableList<CommunityItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return writer_info.size
    }

    override fun getItem(position: Int): Any {
        return writer_info[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var Every_Write_Text = convertView
        if(convertView==null)
        {
            Every_Write_Text = LayoutInflater.from(parent?.context).inflate(R.layout.n_community_every_board_write_item,parent,false)

        }
            val title = Every_Write_Text?.findViewById<TextView>(R.id.every_write_title)
                title!!.text=writer_info[position].title
            val content = Every_Write_Text?.findViewById<TextView>(R.id.every_write_content)
                 content!!.maxLines = 3
                content!!.text=writer_info[position].content
            val time = Every_Write_Text?.findViewById<TextView>(R.id.every_write_time)
                time!!.text=writer_info[position].time
        return  Every_Write_Text!!
    }
}