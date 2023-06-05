package com.example.malangtrip.Nav.Home

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Nav.Home.Jeju.Main_Jeju
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeBinding
//메인 홈
class Main_Home : Fragment(){
    private var _binding: NHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
//    actionBar?.setDisplayHomeAsUpEnabled(true)
//    actionBar?.title = "나의 프로필"
//    actionBar?.setHomeAsUpIndicator(R.drawable.my_home_back) // 홈 버튼 아이콘을 검정색으로 설정
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        _binding = NHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 액션바 설정, 이름변경, 액티비티에 연결되어 있는 프래그먼트이므로 상단 뒤로가기 버튼 없음
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "가고 싶은 여행지를 찾아요"
        actionBar?.setHomeAsUpIndicator(R.drawable.my_home_back) // 홈 버튼 아이콘 변경
        setHasOptionsMenu(true)
        binding.goJeju.setOnClickListener {
            startActivity(Intent(context, Main_Jeju::class.java))
        }
        // 뒤로가기 버튼을 눌렀을 때 앱 종료
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                showExitDialog()
                return@setOnKeyListener true
            }
            false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showExitDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("앱 종료")
            .setMessage("정말로 앱을 종료하시겠습니까?")
            .setPositiveButton("종료") { _, _ ->
                activity?.let {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    it.startActivity(intent)
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                showExitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}