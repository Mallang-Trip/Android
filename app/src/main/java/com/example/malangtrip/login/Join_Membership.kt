package com.example.malangtrip.login

import android.content.Intent
import java.util.concurrent.TimeUnit
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.databinding.BJoinScreenBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


//회원가입
class Join_Membership :AppCompatActivity() {
    lateinit var binding: BJoinScreenBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BJoinScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide() // 액션바 숨김
        mAuth = FirebaseAuth.getInstance()

        binding.joinBtn.setOnClickListener {
            val email = binding.joinIdInput.text.toString()
            val password = binding.joinPasswordInput.text.toString()
            val password_check = binding.joinPasswordCheckInput.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful&&password==password_check) {
                        // 회원가입 성공
                        Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,User_Data_Input::class.java))
                        finish()
                    } else {
                        // 회원가입 실패
                        if(password!=password_check)
                        {
                            Toast.makeText(this, "비밀번호를 똑같이 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(this, "이미 등록된 이메일을 등록하셨거나 이메일 형식으로 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show()
                    }

                }



        }

    }
}
