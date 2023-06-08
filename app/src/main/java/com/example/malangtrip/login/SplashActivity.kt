package com.example.malangtrip.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.malangtrip.Key.DBKey
import com.example.malangtrip.Key.User_Info
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.R
import com.example.malangtrip.databinding.ASplashactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {


    private lateinit var binding : ASplashactivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ASplashactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.AppTheme)
        supportActionBar?.hide() // 액션바 숨김
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        var check = false
        val currentUser = mAuth.currentUser



        val curruntId= Firebase.auth.currentUser?.uid?:""
        Handler(Looper.getMainLooper()).postDelayed({

            Log.d("sdfsdfdsgds",curruntId)
            if(curruntId==null||curruntId=="")
            {
                startActivity(Intent(this, Email_Login::class.java))
                finish()
            }
            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
            mydb.get().addOnSuccessListener {

                val myinfo = it.getValue(User_Info::class.java)?: return@addOnSuccessListener

                val nickName = myinfo.nickname
                if(nickName==null)
                {
                    startActivity(Intent(this, User_Data_Input::class.java))
                    check = true
                    finish()
                    return@addOnSuccessListener
                }
                else{
                    startActivity(Intent(this, Main_Screen::class.java))
                    check = true
                    finish()
                    return@addOnSuccessListener
                }
                if(check==false) {
                    startActivity(Intent(this, Email_Login::class.java))
                    Log.d("지나간다",check.toString())
                    finish()
                }
            }

        }, 1500) // 3000ms = 3초 동안 스플래시 스크린을 보여줍니다.

    }

}