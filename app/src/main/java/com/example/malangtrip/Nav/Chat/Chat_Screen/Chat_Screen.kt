package com.example.malangtrip.Nav.Chat.Chat_Screen

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.Nav.Chat.User.User_Info
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatChatscreenBinding
import com.example.malangtrip.login.DBKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class Chat_Screen : AppCompatActivity() {
    private lateinit var binding : NChatChatscreenBinding
    private lateinit var chatScreenAdapter: Chat_Screen_Adapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var ChatRoomId: String = ""  /////////////////
    private var FriendId: String = ""    /////////////////
    private var MyId: String = ""    /////////////////

    private var FriendFcmToken: String = ""

    private var MyNickname: String = "321321"

    //채팅을 하나씩 받을꺼기 떄문에 리스트 하나 만들어주기
    private var chat_item_list_for_one = mutableListOf<Chat_Screen_Info>()/////////////////////////////

    //전송 버튼 활성화하는 변수
    private var bool_send = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = NChatChatscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val value = intent.getStringExtra("friend_Name")
        val actionBar = supportActionBar
        actionBar?.setTitle("$value")

        ChatRoomId = intent.getStringExtra(EXTRA_CHAT_ROOM_ID)?:return/////////////////
        FriendId = intent.getStringExtra(Extra_Frineds_Id)?:return/////////////////
        MyId = Firebase.auth.currentUser?.uid?:"" /////////////////



        chatScreenAdapter = Chat_Screen_Adapter()
        linearLayoutManager = LinearLayoutManager(applicationContext)
        Firebase.database.reference.child(DBKey.DB_USERS).child(MyId).get()
            .addOnSuccessListener {
                val MyInfo = it.getValue(User_Info::class.java)
               MyNickname = MyInfo?.nickname?:"123" //수정필요
                getFriendData()//상대방 조회
            }//내 정보 조회



        binding.chatRecyclerView.apply {///////////////////
            layoutManager = linearLayoutManager
            adapter =chatScreenAdapter

        }
        chatScreenAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)


                linearLayoutManager.smoothScrollToPosition(binding.chatRecyclerView,null,chatScreenAdapter.itemCount)
            }
        })

        //채팅창 누르면 위로 스크롤롤
       binding.messageEditText.setOnClickListener {
            linearLayoutManager.smoothScrollToPosition(binding.chatRecyclerView,null,chatScreenAdapter.itemCount)
            Log.d("샌드버튼 동작함 닉네임 체크11111","$MyNickname")
        }
        //키보드 나타났을 때 채팅창 위로 올림
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {

                linearLayoutManager.smoothScrollToPosition(binding.chatRecyclerView,null,chatScreenAdapter.itemCount)

        }

        //전송버튼 구현
        binding.sendButton.setOnClickListener {
            val message = binding.messageEditText.text.toString()

            if(bool_send==false)
            {
                return@setOnClickListener
            }
            if(message.isEmpty())
            {
                //나중에 전송버튼 비활성화
                return@setOnClickListener
            }

            val new_Message = Chat_Screen_Info(
                message=message,
                userId=MyId
            )

            Firebase.database.reference.child(DBKey.DB_CHATS).child(ChatRoomId).push().apply {
                new_Message.chatId=key
                setValue(new_Message)
            }
            //db업데이트
            val updates: MutableMap<String,Any> = hashMapOf(
                "${DBKey.DB_CHAT_ROOMS}/$MyId/$FriendId/lastmessage" to message,
                "${DBKey.DB_CHAT_ROOMS}/$FriendId/$MyId/lastmessage" to message,
                "${DBKey.DB_CHAT_ROOMS}/$FriendId/$MyId/chatRoomId" to ChatRoomId,
                "${DBKey.DB_CHAT_ROOMS}/$FriendId/$MyId/friend_Id" to MyId,
                "${DBKey.DB_CHAT_ROOMS}/$FriendId/$MyId/friend_Name" to MyNickname
            )
            Log.d("샌드버튼 동작함 닉네임 체크","$MyNickname")
            Firebase.database.reference.updateChildren(updates)
            //http송신
            val client = OkHttpClient()

            val root = JSONObject()
            val notification = JSONObject()
            notification.put("title", getString(R.string.app_name))
            notification.put("body", message)

            root.put("to", FriendFcmToken)
            root.put("priority", "high")
            root.put("notification", notification)

            val requestBody =
                root.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val request =
                Request.Builder().post(requestBody).url("https://fcm.googleapis.com/fcm/send")
                    .header("Authorization", "key=AAAAx8LZ-Wo:APA91bHXs1xZmDSCP1x6w1Iv-ujY3Xq3h_50w5l5TlDLtdHWdCzh5BBqSHkOdkKXKHrK2tVyB7XAGVjJ1o50YBPIru1UbOg-HNzxzmJSWxAxU1R6TtHNlqDhyk_Xf8Rhw-JENVMKiuly").build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.stackTraceToString()
                }

                override fun onResponse(call: Call, response: Response) {
                    // ignore onResponse
                }

            })
            binding.messageEditText.text.clear()//전송시 에딧 텍스트 비워줌
        }


    }
    //상대방 정보 가져오기
    private fun getFriendData()
    {
        Firebase.database.reference.child(DBKey.DB_USERS).child(FriendId).get()
            .addOnSuccessListener {
                val Friends_Info = it.getValue(User_Info::class.java)
                FriendFcmToken = Friends_Info?.fcmToken.orEmpty()
                chatScreenAdapter.Friends_Item = Friends_Info
                bool_send = true
//                val actionBar = supportActionBar
//                actionBar?.setTitle("${Friends_Info?.nickname}")
                getChatData()//채팅 데이터 불러오기
            }//상대방 조회
    }
    //채팅 정보 가져오기기
    private fun getChatData()
    {
        Firebase.database.reference.child(DBKey.DB_CHATS).child(ChatRoomId).addChildEventListener(object :ChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) { // 어뎁터에 메세지 정보 전송
                val chat_item = snapshot.getValue(Chat_Screen_Info::class.java)
                chat_item?:return
                chat_item_list_for_one.add(chat_item)
                chatScreenAdapter.submitList(chat_item_list_for_one.toMutableList())
            }
            //기능 구현 필요 x
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    //혹시 모를 상황 대비
    companion object{
        const val EXTRA_CHAT_ROOM_ID = "ChatRoomId"
        const val Extra_Frineds_Id = "FriendId"
    }
}