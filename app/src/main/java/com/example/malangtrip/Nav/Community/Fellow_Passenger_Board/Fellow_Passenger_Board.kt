package com.example.malangtrip.Nav.Community.Fellow_Passenger_Board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.malangtrip.databinding.NCommunityFellowPassengerBoardBinding


class Fellow_Passenger_Board : Fragment(){
    private var _binding: NCommunityFellowPassengerBoardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NCommunityFellowPassengerBoardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}