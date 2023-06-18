package com.example.malangtrip.nav.community.boardscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.R

class MyBoardAdapter(val writerInfo :MutableList<CommunityItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return writerInfo.size
    }

    override fun getItem(position: Int): Any {
        return writerInfo[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var everyWriteText = convertView
        everyWriteText = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_board_list,parent,false)



            val title = everyWriteText?.findViewById<TextView>(R.id.tv_writing_title)
            title!!.text=writerInfo[position].title
            val content = everyWriteText?.findViewById<TextView>(R.id.tv_writing_content)
            content!!.maxLines = 3
            content!!.text=writerInfo[position].content
            val time = everyWriteText?.findViewById<TextView>(R.id.tv_time)
            time!!.text=writerInfo[position].time+" / 댓글 수 : "+writerInfo[position].commentNum+"개"
            val writerName = everyWriteText?.findViewById<TextView>(R.id.tv_nickname)
            writerName!!.text=writerInfo[position].userName

        return  everyWriteText!!
    }
}