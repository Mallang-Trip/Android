package com.example.malangtrip.Nav.Home.Jeju

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeJejuBinding

class Main_Jeju : AppCompatActivity(){

    private lateinit var binding: NHomeJejuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NHomeJejuBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val actionBar = supportActionBar
//        actionBar?.setTitle("제주도")

        supportActionBar?.apply {
            title = "제주도로 떠나요"
            val spannableString = SpannableString(title)
            spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            setTitle(spannableString)
        }
        val jeju_driver_list : RecyclerView = findViewById(R.id.jeju_driver_list)

//        val driver_items = ArrayList<Driver_Data>()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_btn, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_btn -> {
                Log.d("체크", "!2323232")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}