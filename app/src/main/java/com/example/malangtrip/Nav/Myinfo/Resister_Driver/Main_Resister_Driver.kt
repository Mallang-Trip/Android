package com.example.malangtrip.Nav.Myinfo.Resister_Driver

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.Nav.Myinfo.Resister_Driver.Fix_Schedule_Price.Main_Fix_Schedule_Price
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoRegisterDriverBinding

//드라이버로 등록하기
class Main_Resister_Driver : Common_Function_Fragment(){
    private var _binding: NMyinfoRegisterDriverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoRegisterDriverBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "드라이버로 등록하기"
        //메뉴 사용 활성화
        setHasOptionsMenu(true)
        //소개글 작성으로 이동
        binding.WriteIntroduction.setOnClickListener {
            val Write_Introduction = Write_Introduction()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Write_Introduction)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //활동 지역
        binding.ActivityArea.setOnClickListener {
            val Activity_Area = Activity_Area()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Activity_Area)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //여행 스케쥴 추가 및 가격 설정
        binding.FixScheduleAddFixPrice.setOnClickListener {
            val Main_Fix_Schedule_Price = Main_Fix_Schedule_Price()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Main_Fix_Schedule_Price)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //활동 가능 날짜
        binding.PossibleDate.setOnClickListener {
            val Possible_Date = Possible_Date()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Possible_Date)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //태그
        binding.TagButton.setOnClickListener {
            val Tag_Function = Tag_Function()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Tag_Function)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //임시 저장
        binding.TemporaryStorage.setOnClickListener {
            val Temporary_Storage = Temporary_Storage()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Temporary_Storage)
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
}