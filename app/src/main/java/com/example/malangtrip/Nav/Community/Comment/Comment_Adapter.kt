package com.example.malangtrip.Nav.Community.Comment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.malangtrip.R


class Comment_Adapter(val commentList : MutableList<Comment_Item>) : BaseAdapter(){
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
       return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var Comment_Text = convertView
        if(Comment_Text==null) {
            Comment_Text = LayoutInflater.from(parent?.context)
                .inflate(R.layout.n_community_comment_adapter, parent, false)
        }
        val title = Comment_Text?.findViewById<TextView>(R.id.UserName)
        title!!.text=commentList[position].commentWriter
        val content = Comment_Text?.findViewById<TextView>(R.id.content)
        content!!.text=commentList[position].commentContent
        val time = Comment_Text?.findViewById<TextView>(R.id.timeArea)
        time!!.text=commentList[position].commentTime


        return  Comment_Text!!
    }

}