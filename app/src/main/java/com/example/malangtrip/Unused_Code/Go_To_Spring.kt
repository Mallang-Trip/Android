package com.example.malangtrip.Unused_Code

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//서버로 데이터 보내기
// User 클래스 작성
class Go_To_Spring {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://example.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun sendUserData(user: User) {
        apiService.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d(TAG, "User created: $user")
                } else {
                    Log.d(TAG, "User creation failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, "Failed to create user", t)
            }
        })
    }

    // User 클래스 작성
    data class User(
        val gender: String,
        val age: String,
        val phonenum: String,
        val nickname: String,
        val accessToken: String,
        val refreshToken: String,
    ){
        val user = User(
            gender,
            age,
            phonenum,
            nickname,
            accessToken,
            refreshToken
        )


    }

    // Retrofit 인터페이스 작성
    interface ApiService {
        @POST("user")
        fun createUser(@Body user: User): Call<User>

        @PUT("user/{id}")
        fun updateUser(@Path("id") id: String, @Body user: User): Call<User>
    }
}