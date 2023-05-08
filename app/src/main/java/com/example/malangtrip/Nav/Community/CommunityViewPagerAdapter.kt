package com.example.malangtrip.Nav.Community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.malangtrip.Nav.Chat.Chat_List.Chat_List
import com.example.malangtrip.Nav.Chat.Search_Friends.Search_Friends
import com.example.malangtrip.Nav.Chat.User.User_List
import com.example.malangtrip.Nav.Community.Every_Board.Every_Board_Screen
import com.example.malangtrip.Nav.Community.Fellow_Passenger_Board.Fellow_Passenger_Board
import com.example.malangtrip.Nav.Community.Free_Write.Free_Write_Board

class CommunityViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 3 // 탭 수에 맞게 설정

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Every_Board_Screen()
            1 -> Fellow_Passenger_Board()
            2 -> Free_Write_Board()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}