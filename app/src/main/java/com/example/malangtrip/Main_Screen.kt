package com.example.malangtrip

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
}