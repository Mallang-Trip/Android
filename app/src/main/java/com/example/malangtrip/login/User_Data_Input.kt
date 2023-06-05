package com.example.malangtrip.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.provider.MediaStore
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.databinding.BLoginUserDataInputBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

//데이터 입력 받기
class User_Data_Input : AppCompatActivity(){
    private lateinit var binding: BLoginUserDataInputBinding
    private var isImageUpload = false
    private val curruntId  = Firebase.auth.currentUser?.uid ?: ""// 현재 유저 아이디 가져오기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BLoginUserDataInputBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val curruntUser = Firebase.auth.currentUser
        if(curruntUser==null)
        {
            startActivity(Intent(this,Email_Login::class.java))
            finish()
        }

        //닉네임 자기 소개 입력 받기
//        binding.goFirebase.setOnClickListener {
//            if(isImageUpload == true) {
//                imageUpload(curruntId)
//            }
//            val nickname = binding.InputNickname.text.toString()
//            val description = binding.InputIntroduce.text.toString()
//            if (nickname.isEmpty()) {
//                Toast.makeText(this, "유저이름은 빈 값으로 둘 수 없습니다", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
//            val myprofile = mutableMapOf<String, Any>()
//
//            myprofile["nickname"] = nickname
//            myprofile["description"] = description
//            mydb.updateChildren(myprofile)
//
//        }
        binding.selectProfilePhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }

        binding.goToMain.setOnClickListener {
            if(isImageUpload == true) {
                imageUpload(curruntId)
            }
            val nickname = binding.InputNickname.text.toString()
            val description = binding.InputIntroduce.text.toString()
            val bank = binding.InputBank.text.toString()
            val bank_Num = binding.InputBankNum.text.toString()
            val currentUser = Firebase.auth.currentUser
            val email = currentUser?.email.toString()


            if (nickname.isEmpty()||bank.isEmpty()||bank_Num.isEmpty()) {
                Toast.makeText(this, "닉네임, 계좌번호, 거래은행은 빈 값으로 둘 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
            val myprofile = mutableMapOf<String, Any>()
            myprofile["userId"] = email
            myprofile["nickname"] = nickname
            myprofile["description"] = description
            myprofile["bankNum"] = bank_Num
            myprofile["bank"] =  bank
            mydb.updateChildren(myprofile)
            startActivity(Intent(this,Complete_Join::class.java))
            finish()
        }


    }
    private fun imageUpload(key : String){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        val imageView = binding.selectProfilePhoto
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.selectProfilePhoto.setImageURI(data?.data)
        }

    }

}