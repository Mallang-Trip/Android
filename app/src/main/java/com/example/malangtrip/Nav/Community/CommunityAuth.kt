package com.example.malangtrip.Nav.Community

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class CommunityAuth {
    companion object{
        private lateinit var auth: FirebaseAuth
        fun getUid() : String{
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }
        fun getTime() : String{
            val currentDate = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDate)
            return dateFormat
        }
    }
}