package com.example.malangtrip.Key

//유저 정보
data class User_Info(
    val userId: String?=null,
    val name: String?=null,
    val email:String?=null,
    val phoneNumner:Int?=null,
    val gender:Boolean?=null,
    val nickname: String?=null,
    val description: String?=null,
    val fcmToken: String?=null,
    val driver_Check: Boolean=false,
    val bank : String?=null,
    val bankNum:String?=null

)