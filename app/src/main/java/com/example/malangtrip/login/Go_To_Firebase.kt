package com.example.malangtrip.login


import android.content.Intent
import android.graphics.Paint.Join
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.R
import com.example.malangtrip.databinding.BMainLoginScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//로그인 화면
class Go_To_Firebase: AppCompatActivity() {
    private lateinit var binding: BMainLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BMainLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    //회원가입으로 이동
        binding.joinBtn.setOnClickListener {
            startActivity(Intent(this,Join_Membership::class.java))
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.idInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if(email.isEmpty()||password.isEmpty())
            {
                Toast.makeText(this,"이메일 또는 패스워드가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task->
                if(task.isSuccessful)
                {
                    startActivity(Intent(this,User_Data_Input::class.java))
                    finish()
                }
                else{
                    Log.e("로그인실패원인",task.exception.toString())
                    Toast.makeText(this,"로그인에 실패했습니다",Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}

