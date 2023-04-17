package com.example.malangtrip.Nav.Myinfo.Resister_Driver.Fix_Schedule_Price

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoRegisterDriverFixTripBinding

//여행 스케쥴 추가 및 가격 설정
class Main_Fix_Schedule_Price : Common_Function_Fragment(){
    private var _binding: NMyinfoRegisterDriverFixTripBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoRegisterDriverFixTripBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "여행 스케쥴 추가 및 가격 설정"
        //메뉴 사용 활성화
        setHasOptionsMenu(true)
        //시간대 별 코스 추가 / 변경 / 삭제로 이동
        binding.AddFixDelete.setOnClickListener {
            val Add_Fix_Delete_Course = Add_Fix_Delete_Course()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Add_Fix_Delete_Course)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //코스 이동 소요 시간 입력
        binding.InputTime.setOnClickListener {
            val Input_Time = Input_Time()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Input_Time)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //가격 설정 및 저장으로 이동
        binding.SettingPrice.setOnClickListener {
            val Setting_Price_Save = Setting_Price_Save()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Setting_Price_Save)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //임시 저장 / 저장 및 프로필 게제로 이동
        binding.SaveProfilePublish.setOnClickListener {
            val Tempory_Save_Profile_Publish = Tempory_Save_Profile_Publish()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Tempory_Save_Profile_Publish)
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