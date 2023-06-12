package com.example.malangtrip.Key

//선택한 친구 정보
data class Chat_Info(
    val chatRoomId: String?=null,
    val lastMessage: String?=null,
    val lastMessageTime: String?=null,
    val friend_Name: String?=null,
    val friend_Id: String?=null
)