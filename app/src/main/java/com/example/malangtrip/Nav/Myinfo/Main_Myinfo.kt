package com.example.malangtrip.Nav.Myinfo

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.Key.User_Info
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.Nav.Myinfo.My_Profile.Fix_Myprofile
import com.example.malangtrip.Nav.Myinfo.Reservation.Main_Reservation
import com.example.malangtrip.Nav.Myinfo.Driver.Main_Resister_Driver
import com.example.malangtrip.Nav.Myinfo.Driver.Resister_Driver_Schedule
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoBinding
import com.example.malangtrip.Key.DBKey
import com.example.malangtrip.Nav.Community.Board_Screen.MyTextList
import com.example.malangtrip.login.Email_Login
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
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
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        //내 정보-> 프로필 확인
        binding.GoToMyProfile.setOnClickListener {
            val Profile_Check_Fragment = Fix_Myprofile()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Profile_Check_Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //내 정보 -> 예약일정
//        binding.Reservation.setOnClickListener {
//            val Reservation_Fragment = Main_Reservation()
//            val transaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragmentContainer, Reservation_Fragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
        // 드라이버 등록하기로 이동
        binding.ResisterDriver.setOnClickListener {
            val MyText_Check = Main_Resister_Driver()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, MyText_Check)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //나의 작성글로 이동
        binding.MyText.setOnClickListener {
            val MyText_Check = MyTextList()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, MyText_Check)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // 내 여행 정보 등록
        binding.MyScheduleControl.setOnClickListener {
            val curruntId = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
            val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
            mydb.get().addOnSuccessListener {
                val myinfo = it.getValue(User_Info::class.java)?: return@addOnSuccessListener
                val driver_Check = myinfo.driver_Check.toString()
                if(driver_Check=="false")
                {
                    Toast.makeText(context,"드라이버로 등록된 사람만 사용할 수 있는 기능입니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    val MyText_Check = Resister_Driver_Schedule()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainer, MyText_Check)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //requireActivity().onBackPressed()
                val homeFragment = Main_Home()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}