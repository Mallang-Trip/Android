package com.example.malangtrip.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.databinding.BCompleteJoinBinding

class Complete_Join : AppCompatActivity() {
    private lateinit var binding: BCompleteJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BCompleteJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this,Main_Screen::class.java))
            finish()
        }
        binding.goHome.setOnClickListener {
            startActivity(Intent(this,Email_Login::class.java))
            finish()
        }
    }
}