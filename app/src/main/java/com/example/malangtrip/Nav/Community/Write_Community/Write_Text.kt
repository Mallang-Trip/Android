package com.example.malangtrip.Nav.Community.Write_Community


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Chat.User.User_Info
import com.example.malangtrip.Nav.Community.CommunityAuth
import com.example.malangtrip.Nav.Community.CommunityItem
import com.example.malangtrip.R

import com.example.malangtrip.databinding.NCommunityWriteTextBinding
import com.example.malangtrip.login.DBKey
import com.example.malangtrip.login.DBKey.Companion.Community_Key
import com.example.malangtrip.login.DBKey.Companion.DB_URL
import com.example.malangtrip.login.DBKey.Companion.Free_Community_Key
import com.example.malangtrip.login.DBKey.Companion.Passenger_Community_Key
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class Write_Text : AppCompatActivity(){
    private lateinit var binding: NCommunityWriteTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NCommunityWriteTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var selectedRadioButtonId = R.id.select_free // Default value
        binding.checkBoard.setOnCheckedChangeListener { group, checkedId ->
            selectedRadioButtonId = checkedId
        }
        binding.inputBtn.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val content = binding.editContent.text.toString()
            val uid = CommunityAuth.getUid()
            val time = CommunityAuth.getTime()

            val curruntId = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
            val Mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근

            Mydb.get().addOnSuccessListener {
                val myinfo = it.getValue(User_Info::class.java) ?: return@addOnSuccessListener
                val My_Name = myinfo.nickname.toString()



            if (content.trim().isNotEmpty() && title.trim().isNotEmpty()) {

                when (selectedRadioButtonId) {
                    R.id.select_free -> {
                        Firebase.database(DB_URL).reference.child(Community_Key).push()
                            .setValue(CommunityItem(uid, My_Name, title, content, time))
                        Firebase.database(DB_URL).reference.child(Free_Community_Key).push()
                            .setValue(CommunityItem(uid, My_Name, title, content, time))
                    }
                    R.id.select_passenger -> {

                        Firebase.database(DB_URL).reference.child(Community_Key).push()
                            .setValue(CommunityItem(uid, My_Name, title, content, time))
                        Firebase.database(DB_URL).reference.child(Passenger_Community_Key).push()
                            .setValue(CommunityItem(uid, My_Name, title, content, time))
                    }
                }


                finish()

            } else {
                Toast.makeText(this, "내용이나 제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // 사진 한장만 선택할 때
//        binding.imageArea.setOnClickListener {
//            val gallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//           startActivityForResult(gallery, 100)
//        }
        //사진 여러장 선택할 때
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(gallery, 100)
        }
    }
            //사진 한장만 선택 할 때
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode== RESULT_OK && requestCode ==100)
//        {
//            binding.imageArea.setImageURI( data?.data)
//        }
//    }
            //사진 여러장 선택할 때
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
                if (resultCode == RESULT_OK && requestCode == 100) {
                    val imageUris = mutableListOf<Uri>()
                    data?.clipData?.let { clipData ->
                        for (i in 0 until clipData.itemCount) {
                            imageUris.add(clipData.getItemAt(i).uri)
                        }
                    } ?: data?.data?.let { singleImageUri ->
                        imageUris.add(singleImageUri)
                    }

                    val imageAdapter = ImageAdapter(imageUris)
                    binding.imageList.adapter = imageAdapter
                }
    }
}