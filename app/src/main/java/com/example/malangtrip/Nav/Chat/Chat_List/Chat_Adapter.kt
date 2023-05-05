package com.example.malangtrip.Nav.Chat.Chat_List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.databinding.NChatChatroomBinding

class Chat_Adapter(private val onclick: (Chat_Info)->Unit) : ListAdapter<Chat_Info, Chat_Adapter.ViewHolder>(differ){

    inner class ViewHolder(private val binding: NChatChatroomBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Chat_Info)
        {
            binding.nicknameTextView.text = item.friend_Name
            binding.lastmessagetext.text = item.lastmessage

            binding.root.setOnClickListener {
                onclick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NChatChatroomBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        val differ = object: DiffUtil.ItemCallback<Chat_Info>()
        {
            override fun areContentsTheSame(oldItem: Chat_Info, newItem: Chat_Info): Boolean {
                return oldItem.chatRoomId == newItem.chatRoomId
            }

            override fun areItemsTheSame(oldItem: Chat_Info, newItem: Chat_Info): Boolean {
                return oldItem == newItem
            }
        }
    }
}