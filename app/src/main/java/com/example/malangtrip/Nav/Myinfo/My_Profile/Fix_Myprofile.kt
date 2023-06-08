package com.example.malangtrip.Nav.Myinfo.My_Profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Key.User_Info
import com.example.malangtrip.databinding.NMyinfoProfileCheckFixProfileBinding
import com.example.malangtrip.Key.DBKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//프로필 변경
class Fix_Myprofile : Fragment() {
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
        actionBar?.title = "나의 프로필"

        //현재 내 정보 가져오기
        val curruntId=Firebase.auth.currentUser?.uid?:"" // 현재 유저 아이디 가져오기
        val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
        val currentUser = Firebase.auth.currentUser
        val email = currentUser?.email.toString()
        mydb.get().addOnSuccessListener {
                val myinfo = it.getValue(User_Info::class.java)?: return@addOnSuccessListener
            binding.fixNicknameEdit.setText(myinfo.nickname)
            binding.fixDesEdit.setText(myinfo.description)
            //binding.InputBank.setText(myinfo.bank)
            binding.myId.setText(" "+email)
            //myinfo.bankNum?.let { it1 -> binding.InputBankNum.setText(it1) }
        }
        //수정한거 적용하는 버튼튼
       binding.applyBtn.setOnClickListener {
           val Nickname = binding.fixNicknameEdit.text.toString()
           val Description = binding.fixDesEdit.text.toString()

           if (Nickname.isEmpty()) {
               Toast.makeText(context, "유저이름은 빈 값으로 둘 수 없습니다", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
           val curruntId = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
           val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
           val mydb_in_friends = Firebase.database.reference.child(DBKey.DB_Friends)//내 정보 접근
           //제이슨업데이트
           val myprofile = mutableMapOf<String, Any>()
           myprofile["nickname"] = Nickname
           myprofile["description"] = Description
           mydb.updateChildren(myprofile)
           //friend내에 모든 나의 닉네임 찾아서 변경하는 코드 작성해야함
          // mydb_in_friends.updateChildren(myprofile)
           // Friend 내에 모든 나의 닉네임 찾아서 변경하는 코드
           mydb_in_friends.addListenerForSingleValueEvent(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   for (user in snapshot.children) { // 모든 사용자에 대해서
                       for (friend in user.children) { // 각 사용자의 친구 목록에 대해서
                           val friendNickname = friend.child("nickname").getValue(String::class.java)
                           if (friendNickname == Nickname) {
                               friend.ref.child("description").setValue(Description)
                           }
                       }
                   }
               }

               override fun onCancelled(error: DatabaseError) {
                   //Log.w(TAG, "Failed to read value.", error.toException())
               }
           })
           requireActivity().supportFragmentManager.popBackStack()
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
    binding.cancleButton.setOnClickListener {
        requireActivity().supportFragmentManager.popBackStack()
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