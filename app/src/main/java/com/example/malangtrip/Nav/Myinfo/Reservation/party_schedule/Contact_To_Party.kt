package com.example.malangtrip.Nav.Myinfo.Reservation.party_schedule

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.databinding.NMyinfoReservationControlScheduleContactPartyBinding

//파티원에게 쪽지 / 채팅 /전화
class Contact_To_Party : Common_Function_Fragment() {
    private var _binding: NMyinfoReservationControlScheduleContactPartyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoReservationControlScheduleContactPartyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "여행일정표, 인원 명단, 수익금 정산"
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