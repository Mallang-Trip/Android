package com.example.malangtrip.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.malangtrip.key.DBKey
import com.example.malangtrip.key.UserInfo
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.databinding.ActivitySplashBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide() // 액션바 숨김
        var loginCheck = false
        val curruntId= Firebase.auth.currentUser?.uid?:""


        Handler(Looper.getMainLooper()).postDelayed({
            if(curruntId==null||curruntId=="")
            {
                startActivity(Intent(this, EmailLogin::class.java))
                finish()
            }
            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
            mydb.get().addOnSuccessListener {

                val myinfo = it.getValue(UserInfo::class.java)?: return@addOnSuccessListener

                val nickName = myinfo.nickname
                if(nickName==null)
                {
                    startActivity(Intent(this, UserDataInput::class.java))
                    loginCheck = true
                    finish()
                    return@addOnSuccessListener
                }
                else{
                    startActivity(Intent(this, Main_Screen::class.java))
                    loginCheck = true
                    finish()
                    return@addOnSuccessListener
                }
                if(loginCheck==false) {
                    startActivity(Intent(this, EmailLogin::class.java))
                    finish()
                }
            }

        }, 1500) // 3000ms = 3초 동안 스플래시 스크린을 보여줍니다.

    }

}