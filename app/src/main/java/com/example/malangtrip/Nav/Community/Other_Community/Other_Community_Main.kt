package com.example.malangtrip.Nav.Community.Other_Community

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.Nav.Commom_Function_Fragment
import com.example.malangtrip.databinding.NCommunityTextOtherBinding
import com.example.malangtrip.databinding.NCommunityWriteTextBinding

class Other_Community_Main : Commom_Function_Fragment() {
    private var _binding: NCommunityTextOtherBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NCommunityTextOtherBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "타인이 쓴 게시글"
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
    //상단 위 뒤로가기 버튼 기능
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