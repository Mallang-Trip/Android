package com.example.malangtrip.Nav.Myinfo.Profile_Check

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoProfileCheckBinding
//프로필 확인
class Main_Profile_Check : Common_Function_Fragment() {

    private var _binding: NMyinfoProfileCheckBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoProfileCheckBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "프로필 확인"
//        //메뉴 사용 활성화
        setHasOptionsMenu(true)
//        //프로필 확인 -> 프로팔 사진
        binding.ProfilePhoto.setOnClickListener {
            val Profile_Check_Photo = Profile_Photo()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Profile_Check_Photo)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //프로필 확인 -> 제목 및 소개글
        binding.MyTitle.setOnClickListener {
            val Title = My_Title()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Title)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //프로필 확인 -> 여행 스케쥴 추가 및 가격 설정
        binding.DriverMyTripFix.setOnClickListener {
            val Schedule = Fix_Driver_Schedule()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Schedule)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //프로필 확인 -> 나의 차 정보 입력 및 수정
        binding.MyCarFix.setOnClickListener {
            val Fix_Car = Fix_Driver_Carinfo()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Fix_Car)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //프로필 확인 -> 프로필 변경
        binding.FIxProfile.setOnClickListener {
            val Fix_Myprofile = Fix_Myprofile()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Fix_Myprofile)
            transaction.addToBackStack(null)
            transaction.commit()
        }
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

    //상단 뒤로가기 버튼 눌렀을 때 이전 프래그먼트로
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}