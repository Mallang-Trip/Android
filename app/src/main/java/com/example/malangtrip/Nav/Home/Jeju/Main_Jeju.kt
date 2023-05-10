package com.example.malangtrip.Nav.Home.Jeju

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.Nav.Home.Driver_Write.Driver_Adapter
import com.example.malangtrip.Nav.Home.Driver_Write.Driver_Data
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeJejuBinding

class Main_Jeju : AppCompatActivity(){

    private lateinit var binding: NHomeJejuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NHomeJejuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jeju_driver_list : RecyclerView = findViewById(R.id.jeju_driver_list)

        val driver_items = ArrayList<Driver_Data>()
        driver_items.add(Driver_Data("진현준","제주의 아들 진현준과 떠나는 여행","나는 제주도의 신이다","11"))


        val jeju_driver_list_adapter = Driver_Adapter(driver_items)
        jeju_driver_list.adapter = jeju_driver_list_adapter

        jeju_driver_list.layoutManager = GridLayoutManager(this,2)
    }
}