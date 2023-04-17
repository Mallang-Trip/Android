package com.example.malangtrip.Nav.Home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.malangtrip.databinding.NCommunityBinding
import com.example.malangtrip.databinding.NHomeBinding
//메인 홈
class Main_Home : Fragment(){
    private var _binding: NHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        _binding = NHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
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

}