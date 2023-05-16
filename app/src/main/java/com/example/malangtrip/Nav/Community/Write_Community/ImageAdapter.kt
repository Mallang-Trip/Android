package com.example.malangtrip.Nav.Community.Write_Community

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.R

class ImageAdapter (private val imageUris: List<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_Item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.n_community_write_text_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageURI(imageUris[position])

    }

    override fun getItemCount() = imageUris.size
    fun getImageUris(): List<Uri> {
        return imageUris
    }
}