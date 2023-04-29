package com.example.malangtrip.Nav.Myinfo.Profile_Check

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.databinding.NMyinfoProfileCheckFixProfileBinding
import com.example.malangtrip.login.Email_Login
import com.google.firebase.auth.ktx.auth
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

        //수정한거 적용하는 버튼튼
       binding.applyBtn.setOnClickListener {
            val Nickname = binding.fixNicknameEdit.text.toString()
            val description = binding.fixDescriptionEdit.text.toString()

           if(Nickname.isEmpty())
           {
               Toast.makeText(context,"유저이름은 빈 값으로 둘 수 없습니다",Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
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