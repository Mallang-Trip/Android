package com.example.malangtrip.Nav.Chat.Chat_List

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.malangtrip.databinding.NChatChatroomBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Chat_Adapter(private val onclick: (Chat_Info)->Unit) : ListAdapter<Chat_Info, Chat_Adapter.ViewHolder>(differ){

    inner class ViewHolder(private val binding: NChatChatroomBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Chat_Info)
        {
            item?.friend_Id?.let { getImageData(it,binding.profileImageView) }
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
    private fun getImageData(key : String,imageView: ImageView){

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(imageView.context)
                .load(uri)
                .into(imageView)
        }.addOnFailureListener {
            // Handle any errors
        }


    }
}