package com.example.malangtrip.Nav.Home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.R


import com.example.malangtrip.databinding.NHomeLocalAdapterBinding



class LocalAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<LocalAdapter.ViewHolder>() {
    // Define the listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    // Set the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NHomeLocalAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.bind(imageResId)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(private val binding: NHomeLocalAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageResId: Int) {
            binding.imageView5.setImageResource(imageResId)
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Call the onItemClick method of the listener
                    listener?.onItemClick(position)
                }
            }
        }
    }
}