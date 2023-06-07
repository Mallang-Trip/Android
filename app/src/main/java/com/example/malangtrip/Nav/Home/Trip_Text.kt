package com.example.malangtrip.Nav.Home

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Chat.Chat_List.Chat_Info
import com.example.malangtrip.Nav.Chat.Chat_Screen.Chat_Screen
import com.example.malangtrip.Nav.Community.CommunityAuth
import com.example.malangtrip.Nav.Community.CommunityItem
import com.example.malangtrip.Nav.Myinfo.Driver.Driver_Info.Driver_Info
import com.example.malangtrip.Nav.Myinfo.Driver.Driver_Info.Trip_Info
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeTripTextBinding
import com.example.malangtrip.login.DBKey
import com.example.malangtrip.login.User_Info
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class Trip_Text : AppCompatActivity(){
    lateinit var binding : NHomeTripTextBinding
    lateinit var driver_Id : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NHomeTripTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trip_Id = intent.getStringExtra("trip_Id").toString()
        driver_Id = intent.getStringExtra("driver_Id").toString()



        getTripData(trip_Id)
        binding.checkProfile.setOnClickListener {
            //val intent = Intent(this, Chat_Screen::class.java)
            //val intent = Intent(this,)
        }
        binding.chatBtn.setOnClickListener {
            val My_Id = Firebase.auth.currentUser?.uid ?: ""
            val chat_room_db = Firebase.database.reference.child(DBKey.DB_CHAT_ROOMS).child(My_Id)
                .child(driver_Id)
            val driver_Key = Firebase.database.reference.child(DBKey.DB_USERS).child(driver_Id)
            driver_Key.get().addOnSuccessListener {
                val driver = it.getValue(User_Info::class.java)

                chat_room_db.get().addOnSuccessListener {
                    var chat_rood_id = ""
                    if (it.value != null) {
                        val chat_room = it.getValue(Chat_Info::class.java)
                        chat_rood_id = chat_room?.chatRoomId ?: ""
                    } else {
                        chat_rood_id = UUID.randomUUID().toString()
                        val new_chat_room = Chat_Info(
                        chatRoomId = chat_rood_id,
                            friend_Name = driver?.nickname,
                            friend_Id = driver?.userId


                        )
                        chat_room_db.setValue(new_chat_room)
                    }
                    val intent = Intent(this, Chat_Screen::class.java)
                    intent.putExtra(Chat_Screen.Extra_Frineds_Id, driver?.userId)
                    intent.putExtra(Chat_Screen.EXTRA_CHAT_ROOM_ID, chat_rood_id)
                    intent.putExtra("friend_Name", driver?.nickname)
                    startActivity(intent)
                }
            }
        }


    }
    private fun getTripData(key:String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(Trip_Info::class.java)


                    //binding.boardTitle.text = dataModel!!.title
                    supportActionBar?.apply {
                        title = "여행 제목 : " + dataModel!!.title
                        setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 표시
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.my_home_back)
                    }

                    binding.tripDate.text = "여행 일정 : " + dataModel!!.schedule
                    binding.writeTime.text = "작성 시간 : " +CommunityAuth.getTime()
                    binding.tripPrice.text="가격 : " + dataModel!!.price+"원"
                    binding.tripContent.text = "여행 내용 : " + dataModel!!.content

                } catch (e: Exception) {

                    Log.d(ContentValues.TAG, "삭제완료")

                }





            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        Firebase.database(DBKey.DB_URL).reference.child(DBKey.Trip_Info).child(driver_Id).child(key).addValueEventListener(postListener)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}