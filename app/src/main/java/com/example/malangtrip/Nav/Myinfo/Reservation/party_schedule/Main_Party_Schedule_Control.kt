package com.example.malangtrip.Nav.Myinfo.Reservation.party_schedule

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.Nav.Myinfo.Myinfo_Fix
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoReservationControlScheduleBinding
//파티 일정 관리
class Main_Party_Schedule_Control : Common_Function_Fragment() {
    private var _binding: NMyinfoReservationControlScheduleBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoReservationControlScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "파티 일정 관리"
        //메뉴 사용 활성화
        setHasOptionsMenu(true)
        //여행 일정표, 인원 명단, 수익금 정산
        binding.ScheduleRosterSettle.setOnClickListener {
            val Contact_To_Party = Contact_To_Party()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Contact_To_Party)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //파티 일정 변경 / 취소
        binding.PartyFix.setOnClickListener {
            val Fix_Date = Contact_To_Party()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Fix_Date)
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