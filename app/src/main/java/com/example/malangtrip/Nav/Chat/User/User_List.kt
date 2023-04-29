package com.example.malangtrip.Nav.Chat.User

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatUserlistBinding

//채팅 목록 EX)카톡 두번째 페이지
class User_List : Fragment(R.layout.n_chat_userlist) {
    private lateinit var binding : NChatUserlistBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NChatUserlistBinding.bind(view)
        val chatlistadapter = User_Adapter()
        binding.userListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = chatlistadapter
        }
        chatlistadapter.submitList(
            mutableListOf<User_Info?>().apply {
                add(User_Info("11","22","33"))
            }
        )

    }
}