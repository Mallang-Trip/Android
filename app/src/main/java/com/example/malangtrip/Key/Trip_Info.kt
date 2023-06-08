package com.example.malangtrip.Key

import com.example.malangtrip.Nav.Myinfo.Driver.Resister_Driver_Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

//val nickname: String?=null,
data class Trip_Info (
        val tripwriterId: String?=null,
        val tripId : String?=null,
        val local: String?=null,
        val title: String?=null,
        val schedule: String?=null,
        val content: String?=null,
        val price: String?=null
)