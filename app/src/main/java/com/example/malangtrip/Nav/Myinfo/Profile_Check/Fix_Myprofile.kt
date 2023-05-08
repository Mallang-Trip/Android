package com.example.malangtrip.Nav.Myinfo.Profile_Check

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Chat.User.User_Info
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.databinding.NMyinfoProfileCheckFixProfileBinding
import com.example.malangtrip.login.DBKey
import com.example.malangtrip.login.Email_Login
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//프로필 변경
class Fix_Myprofile : Common_Function_Fragment() {
    private var _binding: NMyinfoProfileCheckFixProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoProfileCheckFixProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "프로필 변경"
        //현재 내 정보 가져오기
        val curruntId=Firebase.auth.currentUser?.uid?:"" // 현재 유저 아이디 가져오기
        val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
        mydb.get().addOnSuccessListener {
                val myinfo = it.getValue(User_Info::class.java)?: return@addOnSuccessListener
            binding.fixNicknameEdit.setText(myinfo.nickname)
            binding.fixDescriptionEdit.setText(myinfo.description)
        }
        //수정한거 적용하는 버튼튼
       binding.applyBtn.setOnClickListener {
           val Nickname = binding.fixNicknameEdit.text.toString()
           val Description = binding.fixDescriptionEdit.text.toString()

           if (Nickname.isEmpty()) {
               Toast.makeText(context, "유저이름은 빈 값으로 둘 수 없습니다", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
           val curruntId = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
           val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
           val mydb_in_friends = Firebase.database.reference.child(DBKey.DB_Friends).child(curruntId)//내 정보 접근
           //제이슨업데이트
           val myprofile = mutableMapOf<String, Any>()
           myprofile["nickname"] = Nickname
           myprofile["description"] = Description
           mydb.updateChildren(myprofile)
           //friend내에 모든 나의 닉네임 찾아서 변경하는 코드 작성해야함
          // mydb_in_friends.updateChildren(myprofile)

       }
        //로그아웃 버튼
        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, Email_Login::class.java))
            activity?.finish()
        }
        //메뉴 사용 활성화
        setHasOptionsMenu(true)
        // 뒤로가기 버튼 처리 이전 프래그먼트로 감
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().supportFragmentManager.popBackStack()
                true
            } else {
                false
            }
        }

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //requireActivity().onBackPressed()
                requireActivity().supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}