package com.example.malangtrip.login

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.databinding.BLoginUserDataInputBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
            startActivity(Intent(this,Email_Login::class.java))
            finish()
        }
        //닉네임 자기 소개 입력 받기
        binding.goFirebase.setOnClickListener {
            val nickname = binding.InputNickname.text.toString()
            val description = binding.InputIntroduce.text.toString()
            if (nickname.isEmpty()) {
                Toast.makeText(this, "유저이름은 빈 값으로 둘 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val curruntId = Firebase.auth.currentUser?.uid ?: ""// 현재 유저 아이디 가져오기
            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
            val myprofile = mutableMapOf<String, Any>()

            myprofile["nickname"] = nickname
            myprofile["description"] = description
            mydb.updateChildren(myprofile)

        }

        binding.goToMain.setOnClickListener {
            startActivity(Intent(this,Main_Screen::class.java))
            finish()
        }


    }



}