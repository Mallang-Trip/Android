package com.example.malangtrip.Nav.Chat.User

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.Nav.Chat.Chat_List.Chat_Info
import com.example.malangtrip.Nav.Chat.Detail_Chat.Chat_Screen
import com.example.malangtrip.Nav.Chat.Detail_Chat.Chat_Screen_Adapter
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatUserlistBinding
import com.example.malangtrip.login.DBKey.Companion.DB_CHAT_ROOMS
import com.example.malangtrip.login.DBKey.Companion.DB_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

//채팅 목록 EX)카톡 두번째 페이지
class User_List : Fragment(R.layout.n_chat_userlist) {
    private lateinit var binding : NChatUserlistBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NChatUserlistBinding.bind(view)
        //상대방이 날 눌렀을 때
        val Userlistadapter = User_Adapter{friend->
            val My_Id =  Firebase.auth.currentUser?.uid?:""
            val chat_room_db = Firebase.database.reference.child(DB_CHAT_ROOMS).child(My_Id).child(friend.userId?:"")

            chat_room_db.get().addOnSuccessListener {
                var chat_rood_id = ""
                if(it.value !=null)
                {
                        val chat_room = it.getValue(Chat_Info::class.java)
                        chat_rood_id = chat_room?.chatId?:""
                }
                else{
                    chat_rood_id = UUID.randomUUID().toString()
                    val new_chat_room = Chat_Info(
                        chatId = chat_rood_id,
                        friend_Name = friend.nickname,
                        friend_Id = friend.userId
                    )
                    chat_room_db.setValue(new_chat_room)
                }
                val intent = Intent(context, Chat_Screen::class.java)
                intent.putExtra(Chat_Screen.Extra_Frineds_Id,friend.userId)
                intent.putExtra(Chat_Screen.EXTRA_CHAT_ROOM_ID,chat_rood_id)
                startActivity(intent)
            }

        }

        binding.userListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = Userlistadapter
        }
        val myId = Firebase.auth.currentUser?.uid?:""
        Firebase.database.reference.child(DB_USERS)
            .addListenerForSingleValueEvent(object:
            ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val userlist = mutableListOf<User_Info>()

               snapshot.children.forEach {
                   val user = it.getValue(User_Info::class.java)
                   user ?: return

                   if(user.userId != myId){
                       userlist.add(user)
                   }

               }
                Userlistadapter.submitList(userlist)
            }
            })
//        chatlistadapter.submitList(
//            mutableListOf<User_Info?>().apply {
//                add(User_Info("11","22","33"))
//            }
//        )

    }
}