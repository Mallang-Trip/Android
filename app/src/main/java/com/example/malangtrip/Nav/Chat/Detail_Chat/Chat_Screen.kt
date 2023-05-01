package com.example.malangtrip.Nav.Chat.Detail_Chat

import android.os.Bundle
import android.os.PersistableBundle
import android.provider.DocumentsContract.Root
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.Nav.Chat.Chat_List.Chat_Adapter
import com.example.malangtrip.Nav.Chat.Chat_List.Chat_Info
import com.example.malangtrip.Nav.Chat.User.User_Info
import com.example.malangtrip.databinding.NChatChatscreenBinding
import com.example.malangtrip.login.DBKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Chat_Screen : AppCompatActivity() {
    private lateinit var binding : NChatChatscreenBinding
    //상대방정보가져옴
    private var ChatRoomId: String = ""
    private var FriendId: String = ""
    private var MyId: String = ""

    //채팅을 하나씩 받을꺼기 떄문에 리스트 하나 만들어주기
    private var chat_item_list_for_one = mutableListOf<Chat_Screen_Info>()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = NChatChatscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ChatRoomId = intent.getStringExtra(EXTRA_CHAT_ROOM_ID)?:return
        FriendId = intent.getStringExtra(Extra_Frineds_Id)?:return
        MyId = Firebase.auth.currentUser?.uid?:""


        val chatScreenAdapter = Chat_Screen_Adapter()

        Firebase.database.reference.child(DBKey.DB_USERS).child(MyId).get()
            .addOnSuccessListener {
                val MyInfo = it.getValue(User_Info::class.java)
                val MyNickname = MyInfo?.nickname
            }//내 정보 조회
        Firebase.database.reference.child(DBKey.DB_USERS).child(FriendId).get().addOnSuccessListener {
            val Friends_Info = it.getValue(User_Info::class.java)
            chatScreenAdapter.Friends_Item = Friends_Info
        }//상대방 조회

        Firebase.database.reference.child(DBKey.DB_CHATS).child(ChatRoomId).addChildEventListener(object :ChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chat_item = snapshot.getValue(Chat_Screen_Info::class.java)
                chat_item?:return

                chat_item_list_for_one.add(chat_item)
                chatScreenAdapter.submitList(chat_item_list_for_one)
            }
                //기능 구현 필요 x
            override fun onCancelled(error: DatabaseError) {
                }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        }

        )

        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)

        }


    }
    //혹시 모를 상황 대비
    companion object{
        const val EXTRA_CHAT_ROOM_ID = "ChatRoomId"
        const val Extra_Frineds_Id = "FriendId"
    }
}