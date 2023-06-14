package com.example.malangtrip.Nav.Community.Comment


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.malangtrip.key.CommentItem
import com.example.malangtrip.R


class Comment_Adapter(val commentList : MutableList<CommentItem>) : BaseAdapter(){
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
        title?.apply {
            text = commentList[position].commentWriter
            setTextColor(Color.BLUE)
        }
        val content = Comment_Text?.findViewById<TextView>(R.id.content)
        content!!.text=commentList[position].commentContent
        val time = Comment_Text?.findViewById<TextView>(R.id.timeArea)
        time!!.text=commentList[position].commentTime




        return  Comment_Text!!
    }



}


//class Comment_Adapter(val commentList: MutableList<Comment_Item>) : RecyclerView.Adapter<Comment_Adapter.CommentViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.n_community_comment_adapter, parent, false)
//        return CommentViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
//        val comment = commentList[position]
//        holder.bind(comment)
//    }
//
//    override fun getItemCount(): Int {
//        return commentList.size
//    }
//
//    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val title: TextView = itemView.findViewById(R.id.UserName)
//        private val content: TextView = itemView.findViewById(R.id.content)
//        private val time: TextView = itemView.findViewById(R.id.timeArea)
//
//        fun bind(comment: Comment_Item) {
//            title.text = comment.commentWriter
//            content.text = comment.commentContent
//            time.text = comment.commentTime
//        }
//    }
//}