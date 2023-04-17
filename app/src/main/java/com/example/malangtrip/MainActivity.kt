package com.example.malangtrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.malangtrip.databinding.ActivityMainBinding
import com.example.malangtrip.login.KakaoLogin
import com.example.malangtrip.login.ToServerTest
import com.example.malangtrip.login.User_Data_Input
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.util.Utility
//메인 액티비티
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

        //로딩화면후 카카오로그인 실행
        //startActivity(Intent(this, KakaoLogin::class.java))

        //카카오 로그인 거쳐서 가기
        binding.KakaoTest.setOnClickListener {
            startActivity(Intent(this, KakaoLogin::class.java))
        }
        // 데이터 입력 과정으로 가기
        binding.InputDataButton.setOnClickListener {
            startActivity(Intent(this, User_Data_Input::class.java))
        }
        // 로그인 과정 다 스킵해서 가기
        binding.GoHome.setOnClickListener {
            startActivity(Intent(this, Main_Screen::class.java))
        }
        //서버로 카카오 데이터 보내기 테스트
        binding.TestToServerButton.setOnClickListener {
            startActivity(Intent(this, ToServerTest::class.java))
        }


    }

}