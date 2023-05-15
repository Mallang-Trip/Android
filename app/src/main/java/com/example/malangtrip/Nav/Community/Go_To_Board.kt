package com.example.malangtrip.Nav.Community

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.databinding.NCommunityBoardBinding


class Go_To_Board : AppCompatActivity(){
    private lateinit var binding: NCommunityBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =NCommunityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        val name = intent.getStringExtra("name").toString()
        val time = intent.getStringExtra("time").toString()

        binding.boardTitle.text = title
        binding.boardContent.text = content
        binding.boardTime.text = time
        binding.boardUserName.text = name


    }
}