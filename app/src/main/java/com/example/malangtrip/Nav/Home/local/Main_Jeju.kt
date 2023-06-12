package com.example.malangtrip.Nav.Home.local

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.Nav.Home.Trip_Adapter
import com.example.malangtrip.Nav.Home.Trip_Text
import com.example.malangtrip.Key.TripInfo
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeJejuBinding
import com.example.malangtrip.Key.DBKey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Main_Jeju : Fragment(){

    private var _binding: NHomeJejuBinding?=null
    private val binding get()=_binding!!
    private lateinit var jejuTripAdapter : Trip_Adapter
    private val jeju_trip_List = mutableListOf<TripInfo>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = NHomeJejuBinding.inflate(inflater,container,false)
        val root: View = binding.root
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle("제주도로 떠나요")
        setHasOptionsMenu(true)
        loadTripData()
        val recyclerView: RecyclerView = binding.jejuDriverList
        jejuTripAdapter = Trip_Adapter(jeju_trip_List){ it->
            val intent = Intent(context,Trip_Text::class.java)
            intent.putExtra("trip_Id",it.tripId)
            intent.putExtra("driver_Id",it.tripwriterId)
            startActivity(intent)
        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = jejuTripAdapter




        return root
    }


    private fun loadTripData() {


        Firebase.database.reference.child(DBKey.Trip_Info)
            .addListenerForSingleValueEvent(object:
                ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    jeju_trip_List.clear()

                    snapshot.children.forEach { parentSnapshot ->
                        parentSnapshot.children.forEach { childSnapshot ->
                            val jeju_Trip = childSnapshot.getValue<TripInfo>()
                            jeju_Trip ?: return
                            jeju_Trip.local?.let { it1 -> Log.d("여행 잘 배껴오나", it1) }
                            if(jeju_Trip.local=="jeju")
                            {
                                jeju_trip_List.add(jeju_Trip)
                            }
                        }
                    }
                    jeju_trip_List.reverse()
                    jejuTripAdapter.notifyDataSetChanged()
                }
            })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //requireActivity().onBackPressed()
                val homeFragment = Main_Home()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}