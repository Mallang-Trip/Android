package com.example.malangtrip.Nav.Wishlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.Main_Screen
import com.example.malangtrip.Nav.Home.Main_Home
import com.example.malangtrip.Nav.Home.Trip_Adapter
import com.example.malangtrip.key.TripInfo
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NWishlistBinding
import com.example.malangtrip.key.DBKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

//찜목록 메인
class Main_Wishlist : Fragment(){
    private var _binding: NWishlistBinding? = null
    private val binding get() = _binding!!
    //private lateinit var jejuTripAdapter : Trip_Adapter
    //    private val jeju_trip_List = mutableListOf<Trip_Info>()
    private lateinit var MyWishListAdpater : Trip_Adapter
    private val My_Wishlist = mutableListOf<TripInfo>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        _binding = NWishlistBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 액션바 설정, 이름변경
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "찜목록"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        loadWishlistData()
        //val recyclerView: RecyclerView = binding.jejuDriverList
        //        jejuTripAdapter = Trip_Adapter(jeju_trip_List){ it->
        //
        //        }
        //        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        //        recyclerView.layoutManager = gridLayoutManager
        //        recyclerView.adapter = jejuTripAdapter
        val recyclerView: RecyclerView = binding.wishlist
        MyWishListAdpater = Trip_Adapter(My_Wishlist){

        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = MyWishListAdpater




        // 뒤로가기 버튼 처리 기본 뒤로가기 버튼 눌렀을 때 홈 프래그먼트로
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                // 현재 프래그먼트가 액티비티에 연결되어 있을 때에만 동작
                if (isAdded) {
                    val mainActivity = activity as? Main_Screen
                    mainActivity?.binding?.navigationView?.selectedItemId = R.id.navigation_home
                }

                val homeFragment = Main_Home()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                true
            } else {
                false
            }
        }
        return root
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
    private fun loadWishlistData() {

        val curruntId = Firebase.auth.currentUser?.uid ?: ""
        Firebase.database.reference.child(DBKey.My_Wishlist).child(curruntId)
            .addValueEventListener(object:
                ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseError", "Query canceled: $error")
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                        My_Wishlist.clear()

                        snapshot.children.forEach { parentSnapshot ->
//                            parentSnapshot.children.forEach { childSnapshot ->
                                val mywishlist = parentSnapshot.getValue<TripInfo>()
//
                              if (mywishlist != null) {
                                    My_Wishlist.add(mywishlist)
                               }
//
//                            }
                        }

                        MyWishListAdpater.notifyDataSetChanged()

                }
            })
    }
}