package com.example.malangtrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.malangtrip.databinding.ActivityMainBinding
import com.example.malangtrip.login.KakaoLogin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //해쉬키 가져오는 코드
        var key = Utility.getKeyHash(this)
        //val curruntUser = Firebase.auth.currentUser
       Log.d("login","LoginKey : $key")
        startActivity(Intent(this, Main_Screen::class.java))
        //로딩화면후 카카오로그인 실행
        //startActivity(Intent(this, KakaoLogin::class.java))


    }

}