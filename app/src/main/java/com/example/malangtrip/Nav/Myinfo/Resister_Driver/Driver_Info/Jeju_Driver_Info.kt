package com.example.malangtrip.Nav.Myinfo.Resister_Driver.Driver_Info

import android.provider.ContactsContract.CommonDataKinds.Nickname

data class Jeju_Driver_Info(
    val nickname: String?=null,
    val description: String?=null,
    val car_Type: String?=null,
    val license_Plate: String?=null,
    val available_Num: String?=null,
    val approved: Boolean=false
)