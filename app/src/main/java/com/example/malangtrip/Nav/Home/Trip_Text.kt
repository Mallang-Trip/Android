package com.example.malangtrip.Nav.Home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.malangtrip.databinding.NHomeTripTextBinding

class Trip_Text : AppCompatActivity(){
    lateinit var binding : NHomeTripTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=NHomeTripTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}