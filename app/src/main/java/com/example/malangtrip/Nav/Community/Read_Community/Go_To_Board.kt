package com.example.malangtrip.Nav.Community.Read_Community

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NCommunityTextBinding

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage



class Go_To_Board : AppCompatActivity(){
    private lateinit var binding: NCommunityTextBinding
    private lateinit var imageAdapter: Board_Image_Adapter
    private val imageUrls = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =NCommunityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        val name = intent.getStringExtra("name").toString()
        val time = intent.getStringExtra("time").toString()
        val key = intent.getStringExtra("key").toString()

        imageAdapter = Board_Image_Adapter(imageUrls)
        binding.imageItems.adapter = imageAdapter
        binding.imageItems.layoutManager = LinearLayoutManager(this)



        getImageData(key)

        binding.boardTitle.text = title
        binding.boardContent.text = content
        binding.boardTime.text = time
        binding.boardUserName.text = name


    }
    private fun getImageData(key : String)
    {
        val storageReference = Firebase.storage.reference
        val imageCount = intent.getIntExtra("imageCount", 0) // Assuming you pass the image count from the previous activity

        for (i in 0 until imageCount) {
            val fileName = "$key-$i.png"
            val imageRef = storageReference.child(fileName)
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                imageUrls.add(uri.toString())
                imageAdapter.notifyDataSetChanged()

            }.addOnFailureListener {
                // Handle any errors
            }
        }


    }
}