package com.example.malangtrip.Nav.Chat.Chat_List

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.Nav.Chat.Chat_Screen.Chat_Screen
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatChatlistBinding
import com.example.malangtrip.login.DBKey.Companion.DB_CHAT_ROOMS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//채팅 목록 EX)카톡 두번째 페이지
class Chat_List : Fragment(R.layout.n_chat_chatlist) {
    private lateinit var binding : NChatChatlistBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NChatChatlistBinding.bind(view)
        //챗어뎁터
        val chatlistadapter = Chat_Adapter{chat_item->
            //눌렀을 씨 채팅방 이동
            val intent = Intent(context, Chat_Screen::class.java)
            intent.putExtra(Chat_Screen.Extra_Frineds_Id,chat_item.friend_Id)
            intent.putExtra(Chat_Screen.EXTRA_CHAT_ROOM_ID,chat_item.chatRoomId)
            intent.putExtra("friend_Name",chat_item.friend_Name)
            startActivity(intent)
        }
        binding.chatListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = chatlistadapter
        }

        val curruntid = Firebase.auth.currentUser?.uid?: return // 파이어베이스현재유저체크
        val Chat_Rooms_Db= Firebase.database.reference.child(DB_CHAT_ROOMS).child(curruntid)//채팅방db

        Chat_Rooms_Db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val chatRoomList = snapshot.children.mapNotNull {
                    it.getValue(Chat_Info::class.java)
                }
                chatlistadapter.submitList(chatRoomList)

            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }
}