package com.example.malangtrip


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.example.malangtrip.databinding.ActivityMainBinding
import com.example.malangtrip.Key.DBKey
import com.example.malangtrip.login.EmailLogin
import com.example.malangtrip.login.UserDataInput
import com.example.malangtrip.Key.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//메인 액티비티
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //해쉬키 가져오는 코드 카카오톡 로그인할 때 사용할 예정
        //var key = Utility.getKeyHash(this)
        //val curruntUser = Firebase.auth.currentUser
      //Log.d("login","LoginKey : $key")


        //로딩화면후 카카오로그인 실행
        //startActivity(Intent(this, KakaoLogin::class.java))

        //카카오 로그인 거쳐서 가기
//        binding.KakaoTest.setOnClickListener {
//            startActivity(Intent(this, KakaoLogin::class.java))
//        }
        // FirebaseAuth 인스턴스 생성
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        // 현재 로그인 상태 확인
        val currentUser = mAuth.currentUser

//        if (currentUser != null) {
//            // 이미 로그인 되어 있으면 Main_Screen 액티비티로 바로 이동
//            startActivity(Intent(this, Main_Screen::class.java))
//            finish() // 현재 액티비티 종료
//        }
//        else{
            val curruntId=Firebase.auth.currentUser?.uid?:""
            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
           mydb.get().addOnSuccessListener {
              val myinfo = it.getValue(UserInfo::class.java)?: return@addOnSuccessListener
//               val bank = myinfo.bank
//               val bank_Num = myinfo.bankNum
               val nickName = myinfo.nickname
               if(/*bank==null||bank_Num==null||*/nickName==null)
               {
                   startActivity(Intent(this, UserDataInput::class.java))
               }
               else{
                   startActivity(Intent(this, Main_Screen::class.java))
               }

          }

        //}
        // 데이터 입력 과정으로 가기


        startActivity(Intent(this, EmailLogin::class.java))

        //서버로 카카오 데이터 보내기 테스트
//        binding.TestToServerButton.setOnClickListener {
//            startActivity(Intent(this, ToServerTest::class.java))
//        }


    }

//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            // FCM SDK (and your app) can post notifications.
//        } else {
//            // 알림권한 없음
//        }
//    }

//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                showPermissionRationalDialog()
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun showPermissionRationalDialog() {
//        AlertDialog.Builder(this)
//            .setMessage("알림 권한이 없으면 알림을 받을 수 없습니다.")
//            .setPositiveButton("권한 허용하기") { _, _ ->
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }.setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
//            .show()
//    }


}