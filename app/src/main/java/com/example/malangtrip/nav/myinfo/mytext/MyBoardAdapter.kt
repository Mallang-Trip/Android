package com.example.malangtrip.nav.community.boardscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.databinding.AdapterBoardListBinding
import com.example.malangtrip.key.ChatListInfo

//class MyBoardAdapter(val writerInfo :MutableList<CommunityItem>) : BaseAdapter() {
//    override fun getCount(): Int {
//        return writerInfo.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return writerInfo[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var everyWriteText = convertView
//        everyWriteText = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_board_list,parent,false)
//
//
//
//            val title = everyWriteText?.findViewById<TextView>(R.id.tv_writing_title)
//            title!!.text=writerInfo[position].title
//            val content = everyWriteText?.findViewById<TextView>(R.id.tv_writing_content)
//            content!!.maxLines = 3
//            content!!.text=writerInfo[position].content
//            val time = everyWriteText?.findViewById<TextView>(R.id.tv_time)
//            time!!.text=writerInfo[position].time+" / 댓글 수 : "+writerInfo[position].commentNum+"개"
//            val writerName = everyWriteText?.findViewById<TextView>(R.id.tv_nickname)
//            writerName!!.text=writerInfo[position].userName
//
//        return  everyWriteText!!
//    }
//}
//private val onclick: (ChatListInfo)->Unit
class MyBoardAdapter(private val onclick: (CommunityItem) ->Unit) : RecyclerView.Adapter<MyBoardAdapter.ViewHolder>()
{
    var myBoardInfo: MutableList<CommunityItem> = mutableListOf()
    class ViewHolder(private val binding: AdapterBoardListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:CommunityItem,onclick: (CommunityItem) ->Unit)
        {
                binding.tvWritingTitle.text=item.title
                binding.tvWritingContent.text=item.content
                binding.tvNickname.text=item.userName
                binding.tvTime.text=item.time+" / 댓글 수" +item.commentNum+"개"
                binding.root.setOnClickListener {
                    onclick(item)
                }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterBoardListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myBoardInfo[position],onclick)
    }

    override fun getItemCount(): Int {
        return myBoardInfo.size
    }
}