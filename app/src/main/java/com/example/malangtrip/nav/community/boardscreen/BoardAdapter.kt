package com.example.malangtrip.nav.community.boardscreen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BoardAdapter(val writerInfo :MutableList<CommunityItem>) : BaseAdapter() {
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
        var writeText = convertView

            writeText = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_board_list,parent,false)


        if(writerInfo[position].userId== Firebase.auth.currentUser?.uid ?: "")
        {
            val title = writeText?.findViewById<TextView>(R.id.tv_writing_title)
            title?.apply {
                text = writerInfo[position].title
                setTextColor(Color.BLUE)
            }
            val content = writeText?.findViewById<TextView>(R.id.tv_writing_content)
            content?.apply {
                maxLines = 3
                text = writerInfo[position].content
                setTextColor(Color.BLUE)
            }
            val time = writeText?.findViewById<TextView>(R.id.tv_time)
            time?.apply {
                text = writerInfo[position].time+" / 댓글 수 : "+writerInfo[position].commentNum+"개"
                setTextColor(Color.BLUE)
            }
            val writer_Name = writeText?.findViewById<TextView>(R.id.tv_nickname)
            writer_Name?.apply {
                text = writerInfo[position].userName
                setTextColor(Color.BLUE)
            }
        }
        else{
            val title = writeText?.findViewById<TextView>(R.id.tv_writing_title)
            title!!.text=writerInfo[position].title
            val content = writeText?.findViewById<TextView>(R.id.tv_writing_content)
            content!!.maxLines = 3
            content!!.text=writerInfo[position].content
            val time = writeText?.findViewById<TextView>(R.id.tv_time)
            time!!.text=writerInfo[position].time+" / 댓글 수 : "+writerInfo[position].commentNum+"개"
            val writer_Name = writeText?.findViewById<TextView>(R.id.tv_nickname)
            writer_Name!!.text=writerInfo[position].userName
        }
        return  writeText!!
    }
}