package com.example.malangtrip.Nav.Myinfo

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.Nav.Myinfo.My_Profile.Fix_Myprofile
import com.example.malangtrip.Nav.Myinfo.Reservation.Main_Reservation
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoBinding
import com.example.malangtrip.login.Email_Login
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//내 정보 메인
class Main_Myinfo : Fragment() {
    private var _binding: NMyinfoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        _binding = NMyinfoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 액션바 설정, 이름변경
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "내 정보"
        actionBar?.setDisplayHomeAsUpEnabled(false)
        //내 정보-> 프로필 확인
        binding.GoToMyProfile.setOnClickListener {
            val Profile_Check_Fragment = Fix_Myprofile()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Profile_Check_Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //내 정보 -> 예약일정
        binding.Reservation.setOnClickListener {
            val Reservation_Fragment = Main_Reservation()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Reservation_Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //내 정보 -> 나의 작성글 내역
//        binding.MyText.setOnClickListener {
//            val MyText_Check = MyText_Check()
//            val transaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragmentContainer, MyText_Check)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
        binding.goLogoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, Email_Login::class.java))
            activity?.finish()
        }

// 뒤로가기 버튼 처리 기본 뒤로가기 버튼 눌렀을 때 홈 프래그먼트로
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                // 현재 프래그먼트가 액티비티에 연결되어 있을 때에만 동작
                if (isAdded) {
                    val mainActivity = activity as? Main_Screen
                    mainActivity?.binding?.navigationView?.selectedItemId = R.id.navigation_home
                }

                val homeFragment = Main_Home()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                true
            } else {
                false
            }
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}