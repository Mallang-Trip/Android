package com.example.malangtrip.Nav.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.malangtrip.Nav.Chat.Chat_List.Chat_List
import com.example.malangtrip.Nav.Chat.Search_Friends.Search_Friends
import com.example.malangtrip.Unused_Code.User.User_Adapter
import com.example.malangtrip.Unused_Code.User.User_List
//채팅 탭 레이아웃 관리하는 어뎁터
class ChatViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 2 // 탭 수에 맞게 설정

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            //0 -> User_List()
            0 -> Chat_List()
            1 -> Search_Friends()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}