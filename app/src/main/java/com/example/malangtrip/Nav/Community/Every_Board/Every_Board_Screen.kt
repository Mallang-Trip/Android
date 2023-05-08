package com.example.malangtrip.Nav.Community.Every_Board

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.malangtrip.Nav.Community.Write_Community.Write_Text
import com.example.malangtrip.databinding.NCommunityEveryBoardWriteBinding



class Every_Board_Screen : Fragment() {
    private var _binding: NCommunityEveryBoardWriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NCommunityEveryBoardWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.writeBtn.setOnClickListener {
            startActivity(Intent(context, Write_Text::class.java))
        }
        return root


    }
}