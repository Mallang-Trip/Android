package com.example.malangtrip.login

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.R
import com.example.malangtrip.databinding.BLoginUserDataInputBinding
import com.example.malangtrip.databinding.BMainScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.KakaoSdk

//데이터 입력 받기
class User_Data_Input : AppCompatActivity(){
    private lateinit var binding: BLoginUserDataInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BLoginUserDataInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val curruntUser = Firebase.auth.currentUser
        if(curruntUser==null)
        {
            startActivity(Intent(this,Go_To_Firebase::class.java))
            finish()
        }
        binding.goToMain.setOnClickListener {
            startActivity(Intent(this,Main_Screen::class.java))
            finish()
        }


    }



}