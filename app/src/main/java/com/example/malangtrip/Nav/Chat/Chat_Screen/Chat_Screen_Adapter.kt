package com.example.malangtrip.Nav.Chat.Chat_Screen

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.Nav.Chat.User.User_Info
import com.example.malangtrip.databinding.NChatChatscreenAdapterBinding

class Chat_Screen_Adapter : ListAdapter<Chat_Screen_Info, Chat_Screen_Adapter.ViewHolder>(differ){
        //Chat_Screen에서 정보 받아오기
    var Friends_Item: User_Info?=null

    inner class ViewHolder(private val binding: NChatChatscreenAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Chat_Screen_Info)
        {
            if(item.userId==Friends_Item?.userId)
            {
                binding.usernameTextView.isVisible = true
                binding.usernameTextView.text = Friends_Item?.nickname
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.START
            }else
            {
                binding.usernameTextView.isVisible = false
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.END
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NChatChatscreenAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        val differ = object: DiffUtil.ItemCallback<Chat_Screen_Info>()
        {
            override fun areContentsTheSame(oldItem: Chat_Screen_Info, newItem: Chat_Screen_Info): Boolean {
                return oldItem.chatId == newItem.chatId
            }

            override fun areItemsTheSame(oldItem: Chat_Screen_Info, newItem: Chat_Screen_Info): Boolean {
                return oldItem == newItem
            }
        }
    }
}