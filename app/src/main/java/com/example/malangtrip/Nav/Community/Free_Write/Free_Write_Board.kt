package com.example.malangtrip.Nav.Community.Free_Write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.malangtrip.databinding.NCommunityFreeWriteBoardBinding

class Free_Write_Board : Fragment(){
    private var _binding: NCommunityFreeWriteBoardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NCommunityFreeWriteBoardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}