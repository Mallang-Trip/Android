package com.example.malangtrip.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.databinding.BLoginScreenBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
//카카오 로그인 페이지
class KakaoLogin : AppCompatActivity(){
    private val callback:(OAuthToken?,Throwable?)->Unit = {token, error ->
        if(error!=null)
        {
            Log.e("login","error.printStackTrace()")
            error.printStackTrace()
        }
        else if (token!=null)
        {
            Log.e("login", "LoginSuccess : $token")
        }
    }
    private lateinit var binding: BLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, "a0d1ab4cd4502733d63c9fa927683fd7")

        binding.KakaoLoginButton.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("login", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("login", "카카오계정으로 로그인 성공 $token")
                    Data_send()

                }
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("login", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i("login", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        Data_send()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }



    }
    private fun Data_send(){
//        val age = binding.User_Data_Input.InputAge.text.toString()
//        val nickname = binding.InputNickname.text.toString()
//
//        val kakaoToken = AccessTokenManager.getInstance().session.accessToken
//        val refreshToken = AccessTokenManager.getInstance().session.refreshToken
//
//        val gender = when(binding.GenderCheck.checkedRadioButtonId) {
//            R.id.man -> "man"
//            R.id.women -> "woman"
//            else -> ""
//        }
//
//        val phonenum = binding.inputPhoneNum.text.toString()
//
//        val user = Go_To_Spring.User(
//            gender,
//            age,
//            phonenum,
//            nickname,
//            "",
//            "",
//        )
//
//        Go_To_Spring().sendUserData(user)
        startActivity(Intent(this, User_Data_Input::class.java))
    }
}