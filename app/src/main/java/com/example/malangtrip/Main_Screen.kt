package com.example.malangtrip

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.malangtrip.Nav.Chat.Main_Chat
import com.example.malangtrip.Nav.Community.Main_Community
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.Nav.Myinfo.Main_Myinfo
import com.example.malangtrip.Nav.Wishlist.Main_Wishlist
import com.example.malangtrip.databinding.BMainScreenBinding
//메인 화면
class Main_Screen : AppCompatActivity() {
    lateinit var binding: BMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //알림권한 물어보기ㅛ
        askNotificationPermission()
        //처음에 홈으로 세팅
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val HomeFragment = Main_Home()
        transaction.replace(R.id.fragmentContainer, HomeFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        binding.navigationView.selectedItemId = R.id.navigation_home
        //밑에 5개 네비바메뉴 선택했을 때 코드
        binding.navigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> { // 홈 버튼 눌렀을 때 모든 프래그먼트 제거 or 홈프래그먼트로
                    val transaction = fragmentManager.beginTransaction()
                    val HomeFragment = Main_Home()
                    transaction.replace(R.id.fragmentContainer, HomeFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    restoreActionBar() // 액션바 원상복구
//                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                    restoreActionBar() // 액션바 원상복구
                    true
                }
                R.id.navigation_chat -> { //채팅 메뉴로
                    val transaction = fragmentManager.beginTransaction()
                    val chatFragment = Main_Chat()
                    transaction.replace(R.id.fragmentContainer, chatFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.navigation_wishlist -> {//찜 목록으로
                    val transaction = fragmentManager.beginTransaction()
                    val wishlistFragment = Main_Wishlist()
                    transaction.replace(R.id.fragmentContainer, wishlistFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.navigation_myinfo -> {//내 정보로
                    val transaction = fragmentManager.beginTransaction()
                    val myinfoFragment = Main_Myinfo()
                    transaction.replace(R.id.fragmentContainer, myinfoFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.navigation_community -> {// 커뮤니티 메뉴로
                    val transaction = fragmentManager.beginTransaction()
                    val communityFragment = Main_Community()
                    transaction.replace(R.id.fragmentContainer, communityFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                else -> false
            }
        }
    }

    //액션바 원상복구하는 함수
    private fun restoreActionBar() {
        supportActionBar?.apply {
            title = "MalangTrip"
            //setDisplayHomeAsUpEnabled(false)
        }
    }
    public fun Nav_Home()
    {
        binding.navigationView.selectedItemId = R.id.navigation_home
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // 알림권한 없음
        }
    }
    //권한 요청하기
    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionRationalDialog()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationalDialog() {
        AlertDialog.Builder(this)
            .setMessage("알림 권한이 없으면 알림을 받을 수 없습니다.")
            .setPositiveButton("권한 허용하기") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            .show()
    }
}