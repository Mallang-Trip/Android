package com.example.malangtrip.Unused_Code

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.malangtrip.databinding.ZUnusedTestToServerBinding


class ToServerTest : AppCompatActivity() {
    lateinit var binding: ZUnusedTestToServerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ZUnusedTestToServerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}