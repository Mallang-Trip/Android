package com.example.malangtrip.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.malangtrip.databinding.TestToServerBinding

class ToServerTest : AppCompatActivity() {
    lateinit var binding: TestToServerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestToServerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}