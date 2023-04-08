package com.example.malangtrip.Nav.Community

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.Nav.Community.Other_Community.Other_Community_Main
import com.example.malangtrip.Nav.Community.Write_Text.Write_Text_Main
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.Nav.Myinfo.Profile_Check.Main_Profile_Check
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NCommunityBinding
import com.example.malangtrip.databinding.NHomeBinding

class Main_Community : Fragment(){

    private var _binding: NCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NCommunityBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 액션바 설정, 이름변경, 액티비티에 연결되어 있는 프래그먼트이므로 상단 뒤로가기 버튼 없음
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = "커뮤니티"
        //메인 커뮤니티 화면 -> 게시글 작성
        binding.WriteText.setOnClickListener {
            val Write_Text_Fragment = Write_Text_Main()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Write_Text_Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //메인 커뮤니티 화면 -> 타인이 쓴 게시글
        binding.OtherText.setOnClickListener {
            val Other_Community_Main_Fragment = Other_Community_Main()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Other_Community_Main_Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // 뒤로가기 버튼 처리 기본 뒤로가기 버튼 눌렀을 때 홈 프래그먼트로
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                // 현재 프래그먼트가 액티비티에 연결되어 있을 때에만 동작
                if (isAdded) {
                    val mainActivity = activity as? Main_Screen
                    mainActivity?.binding?.navigationView?.selectedItemId = R.id.navigation_home
                }

                val homeFragment = Main_Home()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                true
            } else {
                false
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}