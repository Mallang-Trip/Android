package com.example.malangtrip.Nav.Home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.databinding.NHomeDriverProfileScreenBinding

//lateinit var binding : NHomeTripTextBinding
//    lateinit var driver_Id : String
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = NHomeTripTextBinding.inflate(layoutInflater)
//        setContentView(binding.root)

class Driver_Profile : AppCompatActivity(){
    lateinit var binding : NHomeDriverProfileScreenBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = NHomeDriverProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}