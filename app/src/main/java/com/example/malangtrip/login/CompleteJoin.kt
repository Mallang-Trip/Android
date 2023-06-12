package com.example.malangtrip.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.databinding.ActivityBCompleteJoinBinding

class CompleteJoin : AppCompatActivity() {
    private lateinit var binding: ActivityBCompleteJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBCompleteJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //홈으로 이동
        binding.btnGoHome.setOnClickListener {
            startMainScreen()
        }
        //첫 이메일 로그인 화면으로 이동
        binding.btnGoLogin.setOnClickListener {
            goEmailLogin()
        }

    }
    private fun startMainScreen()
    {
            startActivity(Intent(this,Main_Screen::class.java))
            finish()
    }
    private fun goEmailLogin()
    {
        startActivity(Intent(this,EmailLogin::class.java))
        finish()
    }
}