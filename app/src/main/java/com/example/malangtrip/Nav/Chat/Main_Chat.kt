package com.example.malangtrip.Nav.Chat

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.Nav.Chat.User.User_List
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatBinding

class Main_Chat : Fragment(){

    private var _binding: NChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 액션바 설정, 이름변경
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "쪽지"

        binding.goToFriendlist.setOnClickListener {
            val User_List = User_List()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer,User_List )
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.goToChatRoom.setOnClickListener {
            val Chat_List = Chat_List()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer,Chat_List)
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