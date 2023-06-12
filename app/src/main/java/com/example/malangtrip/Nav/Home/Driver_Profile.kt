package com.example.malangtrip.Nav.Home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.malangtrip.Key.DriverInfo
import com.example.malangtrip.Key.TripInfo
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeDriverProfileScreenBinding
import com.example.malangtrip.Key.DBKey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage



class Driver_Profile : AppCompatActivity() {
    lateinit var binding: NHomeDriverProfileScreenBinding
    lateinit var Driver_Trip_Adapter : Trip_Adapter
    private val driver_trip_List = mutableListOf<TripInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NHomeDriverProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageUrl = intent.getStringExtra("image_url")
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = imageUrl?.let { storageRef.child(it) }

//        if (imageUrl != null) {
//            getImageData(imageUrl)
//        }
        imageUrl?.let {
            val imageRef = storageRef.child(it)

            imageRef.downloadUrl.addOnSuccessListener { uri ->
                loadImageWithGlide(uri.toString(), binding.profilePhoto)
            }.addOnFailureListener {
                // 이미지 URL을 얻어오는 데 실패했을 때의 동작을 여기에 작성합니다.
            }
        }
        val imageViewFromFB = binding.profilePhoto

        val DriverKey = intent.getStringExtra("DriverKey").toString()
        if (imageUrl != null) {
            Log.d("Dsdfsdf",imageUrl)
        }
        loadTripData(DriverKey)
        getDriverData(DriverKey)
        if (imageUrl != null) {
            loadImageWithGlide(imageUrl,  imageViewFromFB)
        }
        //(DriverKey)


    }
    private fun getDriverData(Key: String)
    {
        val driver_Key = Firebase.database.reference.child(DBKey.Driver).child(Key)
        driver_Key.get().addOnSuccessListener {


            val driver_Profile = it.getValue(DriverInfo::class.java)
            supportActionBar?.apply {
                title = "드라이버 닉네임 : " + driver_Profile!!.nickname
                setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 표시
                //setHasOptionsMenu(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.my_home_back)
            }
            if (driver_Profile != null) {
                binding.driverLocal.text = "서비스 지역 : " + driver_Profile.local
                binding.driverDes.text = "상태 메세지 : " + driver_Profile.description
                val recyclerView: RecyclerView = binding.driverTripList
                    Driver_Trip_Adapter = Trip_Adapter(driver_trip_List){ it->
                            val intent = Intent(this,Trip_Text::class.java)
                            intent.putExtra("trip_Id",it.tripId)
                            intent.putExtra("driver_Id",it.tripwriterId)
                            startActivity(intent)
                        }
                        val LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        recyclerView.layoutManager = LinearLayoutManager
                        recyclerView.adapter = Driver_Trip_Adapter
            }

        }
    }
    private fun loadTripData(key: String) {


        Firebase.database.reference.child(DBKey.Trip_Info).child(key)
            .addListenerForSingleValueEvent(object:
                ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {//드라이버의 여행 띄우는 중이었음///////////
                    driver_trip_List.clear()

                    snapshot.children.forEach { parentSnapshot ->

                            val driver_Trip = parentSnapshot.getValue<TripInfo>()
                        driver_Trip ?: return
                        driver_Trip.local?.let { it1 -> Log.d("여행 잘 배껴오나", it1) }

                        driver_trip_List.add(driver_Trip)

                        }


                    //Driver_Trip_Adapter.notifyDataSetChanged()
                    if (::Driver_Trip_Adapter.isInitialized) {
                        Driver_Trip_Adapter.notifyDataSetChanged()
                    }
                }
            })
    }
    private fun getImageData(imageUrl : String) {
        val imageViewFromFB = binding.profilePhoto

        Glide.with(this)
            .load(Uri.parse(imageUrl))
            .into(imageViewFromFB)

        Log.d("이미지 잘 불러옴", imageUrl)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
    private fun loadImageWithGlide(imageUrl: String, imageView: ImageView)
    {
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }


}