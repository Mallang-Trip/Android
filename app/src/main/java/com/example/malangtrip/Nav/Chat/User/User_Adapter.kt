package com.example.malangtrip.Nav.Chat.User

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.malangtrip.databinding.NChatUserBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class User_Adapter(private val onClick:(User_Info)->Unit) : ListAdapter<User_Info, User_Adapter.ViewHolder>(differ){

    inner class ViewHolder(private val binding: NChatUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: User_Info)
        {
            item?.userId?.let { getImageData(it,binding.profileImageView) }
            binding.nicknameTextView.text = item.nickname
            binding.descriptionTextView.text = item.description

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NChatUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val differ = object: DiffUtil.ItemCallback<User_Info>()
        {
            override fun areContentsTheSame(oldItem: User_Info, newItem: User_Info): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areItemsTheSame(oldItem: User_Info, newItem: User_Info): Boolean {
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