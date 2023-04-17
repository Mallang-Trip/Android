package com.example.malangtrip.login

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.R
import com.example.malangtrip.databinding.BLoginUserDataInputBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.KakaoSdk

//데이터 입력 받기
class User_Data_Input : AppCompatActivity(){
    private lateinit var binding: BLoginUserDataInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BLoginUserDataInputBinding
            .inflate(layoutInflater)
        setContentView(binding.root)
        //임시 버튼 누르면 앱 시작
        binding.CheckBtn.setOnClickListener {
            startActivity(Intent(this, Main_Screen::class.java))
        }

        binding.button.setOnClickListener {
            val age = binding.InputAge.text.toString()
            val nickname = binding.InputNickname.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(nickname,age).addOnCompleteListener(this){task->
                    if(task.isSuccessful)
                    {
                        Toast.makeText(this,"데이터 등록 성공",Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        Toast.makeText(this,"데이터 등록 실패",Toast.LENGTH_SHORT).show()
                    }
            }
        }
        binding.button2.setOnClickListener {
            val age = binding.InputAge.text.toString()
            val Nickname = binding.InputNickname.text.toString()
            Firebase.auth.signInWithEmailAndPassword(Nickname,age).addOnCompleteListener(this) { task->
                if(task.isSuccessful)
                {
                    startActivity(Intent(this, Main_Screen::class.java))
                    //bToast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.d("firebase1",task.exception.toString())
                    Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
                }
            }
        }
        KakaoSdk.init(this, "your_app_key")

        binding.button3.setOnClickListener {
            var gender = "man"
            var phonenum = "010"
            var age = "aa"
            var nickname = "a"
            when(binding.GenderCheck.checkedRadioButtonId)
            {
                R.id.man ->
                {
                    val gender = "man"
                }
                R.id.women ->
                {
                    val gender = "woman"
                }
            }
            phonenum = binding.inputPhoneNum.text.toString()
            age = binding.InputAge.text.toString()
            nickname = binding.InputNickname.text.toString()
            val user = Go_To_Spring.User(
                gender,
                age,
                phonenum,
                nickname,
                "",
                "",
            )

            Go_To_Spring().sendUserData(user)
        }
    }



}