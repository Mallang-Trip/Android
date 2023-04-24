package com.example.malangtrip.Nav.Chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.malangtrip.databinding.NChatUserBinding

class User_Adapter : ListAdapter<User_Info,User_Adapter.ViewHolder>(differ){

    inner class ViewHolder(private val binding: NChatUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: User_Info)
        {
            binding.nicknameTextView.text = item.NickName
            binding.descriptionTextView.text = item.descriptioin
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        val differ = object: DiffUtil.ItemCallback<User_Info>()
        {
            override fun areContentsTheSame(oldItem: User_Info, newItem: User_Info): Boolean {
                return oldItem.UserId == newItem.UserId
            }

            override fun areItemsTheSame(oldItem: User_Info, newItem: User_Info): Boolean {
                return oldItem == newItem
            }
        }
    }
}