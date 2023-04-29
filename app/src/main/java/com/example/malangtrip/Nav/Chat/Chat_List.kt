package com.example.malangtrip.Nav.Chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatChatlistBinding
import com.example.malangtrip.databinding.NChatUserlistBinding

//채팅 목록 EX)카톡 두번째 페이지
class Chat_List : Fragment(R.layout.n_chat_chatlist) {
    private lateinit var binding : NChatChatlistBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NChatChatlistBinding.bind(view)
        val chatlistadapter = Chat_Adapter()
        binding.chatListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = chatlistadapter
        }
        chatlistadapter.submitList(
            mutableListOf<Chat_Info?>().apply {
                add(Chat_Info("11","22","33"))
            }
        )

    }
}