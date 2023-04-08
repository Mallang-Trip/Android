package com.example.malangtrip.Nav.Community.Write_Text

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.Nav.Commom_Function_Fragment
import com.example.malangtrip.Nav.Community.Choice_Party.Choice_Party_Main
import com.example.malangtrip.Nav.Community.Fix_Delete.Fix_Delete_Main
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NCommunityWriteTextBinding

class Write_Text_Main : Fragment() {
    private var _binding: NCommunityWriteTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NCommunityWriteTextBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "게시글 작성"
        //메뉴 사용 활성화
        setHasOptionsMenu(true)
        //게시글 작성 -> 파티 선택
        binding.CommunityWishlist.setOnClickListener {
            val Choice_Party_Fragment = Choice_Party_Main()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Choice_Party_Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //게시글 작성 -> 수정 및 삭제
        binding.FixDelete.setOnClickListener {
            val Fix_Delete_Fragment = Fix_Delete_Main()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, Fix_Delete_Fragment)
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