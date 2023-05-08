package com.example.malangtrip.Nav.Chat.Search_Friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.malangtrip.Nav.Chat.User.User_Adapter
import com.example.malangtrip.Nav.Chat.User.User_Info
import com.example.malangtrip.Nav.Chat.User.User_List


import com.example.malangtrip.databinding.NChatSearchFriendBinding
import com.example.malangtrip.login.DBKey
import com.example.malangtrip.login.DBKey.Companion.DB_URL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Search_Friends : Fragment(){

    private var _binding: NChatSearchFriendBinding? = null
    private val binding get() = _binding!!
    private var myNickname =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = NChatSearchFriendBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //내 이름 조회
        val currentUserId = Firebase.auth.currentUser?.uid
        if (currentUserId != null) {
            getCurrentUserNickname(currentUserId) { nickname ->
                myNickname = nickname
                Log.d("내 이름 잘 배껴오나", "$myNickname")
            }
        }



        binding.addFriendBtn.setOnClickListener {
            val friend_name = binding.inputFriendName.text.toString()

            if(myNickname==friend_name)
            {
                Toast.makeText(context,"자신의 닉네임은 검색할 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //이름 검색
            findFriend(friend_name)

        }

        return root
    }
    private fun findFriend(friendName: String) {

        // 파이어베이스에서 유저 데이터 가져오기
        val curruntUser = Firebase.auth.currentUser
        val ref = FirebaseDatabase.getInstance().reference.child("Users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var isFound = false // 닉네임을 찾았는지 여부를 체크하는 변수
                    for (userSnapshot in snapshot.children) {
                        val nickname = userSnapshot.child("nickname").value as String
                        if (nickname == friendName) {
                            isFound = true // 닉네임을 찾았음을 표시

                            val myId = curruntUser?.uid

                            val userId = userSnapshot.child("userId").value as String
                            val description = userSnapshot.child("description").value as String
                            val fcmToken = userSnapshot.child("fcmToken").value as String
                            val info_friend = mutableMapOf<String,Any>()
                            info_friend["nickname"]=nickname
                            info_friend["userId"]=userId
                            info_friend["description"]=description
                            info_friend["fcmToken"]=fcmToken
                            Log.d("닉네임확인",nickname)
                            if (myId != null) {
                                Firebase.database(DBKey.DB_URL).reference.child(DBKey.DB_Friends).child(myId).child(userId).updateChildren(info_friend)
                            }
                            break
                        }

                    }
                    if (!isFound) { // 닉네임을 찾지 못했으면
                        Toast.makeText(context,"없는 닉네임", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    // Users 경로에 데이터가 없음
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 쿼리 취소 시
            }
        })


    }
    private fun getCurrentUserNickname(userId: String, onComplete: (String) -> Unit) {
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nickname = snapshot.child("nickname").value as String
                    onComplete(nickname)

                } else {
                    // 유저 데이터가 없음
                    onComplete("")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 쿼리 취소 시
            }
        })
    }



}