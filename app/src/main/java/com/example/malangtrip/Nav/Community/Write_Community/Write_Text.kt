package com.example.malangtrip.Nav.Community.Write_Community


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.malangtrip.databinding.NCommunityWriteTextBinding
import com.example.malangtrip.login.DBKey


class Write_Text : AppCompatActivity(){
    private lateinit var binding: NCommunityWriteTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NCommunityWriteTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputBtn.setOnClickListener {
            val title = binding.editTitle.toString()
            val content = binding.editContent.toString()

        }
    }

}