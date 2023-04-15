package com.example.malangtrip.Nav.Myinfo.Reservation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Commom_Function_Fragment
import com.example.malangtrip.Nav.Myinfo.Profile_Check.Main_Profile_Check
import com.example.malangtrip.Nav.Myinfo.Reservation.party_schedule.Main_Party_Schedule_Control
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoProfileCheckProfilePhotoBinding
import com.example.malangtrip.databinding.NMyinfoReservationBinding

class Main_Reservation : Commom_Function_Fragment() {
    private var _binding: NMyinfoReservationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoReservationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "예약 일정"
        //예약 사안 변경 제안으로 이동
        binding.SuggestResevation.setOnClickListener {
            val Suggest_Reservation= Suggest_Reservation()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Suggest_Reservation)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //예약취소로 이동
        binding.CancelReservation.setOnClickListener {
            val Cancel_Reservation= Cancel_Reservation()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Cancel_Reservation)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //드라이버 1대1 쪽지로 이동
        binding.CancelReservation.setOnClickListener {
            val Cancel_Reservation= Cancel_Reservation()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Cancel_Reservation)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //파티채팅 창으로 이동
        binding.ChatParty.setOnClickListener {
            val Chat_Room_Party= Chat_Room_Party()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Chat_Room_Party)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //파티 일정 관리로 이동
        binding.ControlSchedule.setOnClickListener {
            val Main_Party_Schedule_Control= Main_Party_Schedule_Control()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Main_Party_Schedule_Control)
            transaction.addToBackStack(null)
            transaction.commit()
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