package com.example.malangtrip.Nav.Chat.User

import android.os.Bundle
import android.renderscript.Sampler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NChatUserlistBinding
import com.example.malangtrip.login.DBKey.Companion.DB_USERS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//채팅 목록 EX)카톡 두번째 페이지
class User_List : Fragment(R.layout.n_chat_userlist) {
    private lateinit var binding : NChatUserlistBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NChatUserlistBinding.bind(view)
        val Userlistadapter = User_Adapter()
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

                   if(user.UserId != myId){
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