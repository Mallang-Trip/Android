package com.example.malangtrip.Nav.Community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.malangtrip.Nav.Community.boardscreen.EveryBoardScreen
import com.example.malangtrip.Nav.Community.boardscreen.FellowPassengerBoardScreen
import com.example.malangtrip.Nav.Community.boardscreen.FreeWritingBoardScreen

class CommunityViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 3 // 탭 수에 맞게 설정

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EveryBoardScreen()
            1 -> FreeWritingBoardScreen()
            2 -> FellowPassengerBoardScreen()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}