package com.example.malangtrip.Nav.Myinfo.Driver

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Nav.Myinfo.Driver.Driver_Info.Driver_Info
import com.example.malangtrip.Nav.Myinfo.Driver.Driver_Info.Trip_Info
import com.example.malangtrip.databinding.NMyinfoRegisterDriverScheduleBinding
import com.example.malangtrip.login.DBKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Resister_Driver_Schedule : Fragment() {
    private var _binding: NMyinfoRegisterDriverScheduleBinding? = null
    private val binding get() = _binding!!
    var local = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoRegisterDriverScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "여행 정보 등록하기"
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

        binding.resisterTrip.setOnClickListener {
           val title = binding.tripTitle.text.toString().trim()
            val schedule = binding.tripSchedule.text.toString().trim()
            val content = binding.tripContent.text.toString().trim()
            val price = binding.tripPrice.text.toString().trim()

            if(title.isEmpty()||schedule.isEmpty()||content.isEmpty()||price.isEmpty())
            {
                Toast.makeText(context,"입력 안 된 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                val curruntId = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
                val mydb = Firebase.database.reference.child(DBKey.Driver).child(curruntId)//내 정보 접근
                val key =  Firebase.database(DBKey.DB_URL).reference.push().key.toString()
                mydb.get().addOnSuccessListener {
                    val myinfo = it.getValue(Driver_Info::class.java)?: return@addOnSuccessListener
                    local = myinfo.local.toString()
                    Log.d("sdfsd",local)
                    Firebase.database(DBKey.DB_URL).reference.child(DBKey.Trip_Info)
                        .child(curruntId).child(key)
                        .setValue(
                            Trip_Info(
                                curruntId,key,local,title,schedule, content,price
                            )
                        )
                }

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