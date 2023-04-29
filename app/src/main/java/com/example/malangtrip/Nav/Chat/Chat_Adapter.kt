package com.example.malangtrip.Nav.Chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.databinding.NChatChatroomBinding
import com.example.malangtrip.databinding.NChatUserBinding

class Chat_Adapter : ListAdapter<Chat_Info, Chat_Adapter.ViewHolder>(differ){

    inner class ViewHolder(private val binding: NChatChatroomBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Chat_Info)
        {
            binding.nicknameTextView.text = item.Friend_Name
            binding.lastmessagetext.text = item.lastmessage
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
                return oldItem.ChatId == newItem.ChatId
            }

            override fun areItemsTheSame(oldItem: Chat_Info, newItem: Chat_Info): Boolean {
                return oldItem == newItem
            }
        }
    }
}