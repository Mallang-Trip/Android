package com.example.malangtrip.Nav.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malangtrip.Nav.Home.local.Main_Jeju
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NHomeBinding
import com.example.malangtrip.Key.DBKey
import com.example.malangtrip.databinding.NHomeLocalAdapterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//메인 홈
class Main_Home : Fragment() {
    private var _binding: NHomeBinding? = null
    private lateinit var localAdapter: LocalAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //    val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
//    actionBar?.setDisplayHomeAsUpEnabled(true)
//    actionBar?.title = "나의 프로필"
//    actionBar?.setHomeAsUpIndicator(R.drawable.my_home_back) // 홈 버튼 아이콘을 검정색으로 설정
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
// 앱 시작 시 찜 목록을 불러옵니다.///////////
        Get_Community_Info()
        Get_MyWishlist_Info()
        Get_MyInfo()
        Get_TripInfo()

        _binding = NHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 액션바 설정, 이름변경, 액티비티에 연결되어 있는 프래그먼트이므로 상단 뒤로가기 버튼 없음
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "가고 싶은 여행지를 찾아요"
        actionBar?.setHomeAsUpIndicator(R.drawable.my_home_back) // 홈 버튼 아이콘 변경
        setHasOptionsMenu(true)
        // Create a list of image resource IDs or URLs
        val imageList = listOf(R.drawable.jeju,R.drawable.comming_soon,R.drawable.comming_soon,R.drawable.comming_soon,R.drawable.comming_soon
        ,R.drawable.comming_soon,R.drawable.comming_soon,R.drawable.comming_soon)

        // Initialize the RecyclerView and adapter
        localAdapter = LocalAdapter(imageList)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = localAdapter
        localAdapter.setOnItemClickListener(object : LocalAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle the item click event here
                if (position == 0) {
                    val jeju_Fragment = Main_Jeju()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainer, jeju_Fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                else{
                    Toast.makeText(context, "곧 다른 지역이 추가될 예정입니다!\n기대해주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // 뒤로가기 버튼을 눌렀을 때 앱 종료
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                showExitDialog()
                return@setOnKeyListener true
            }
            false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("앱 종료")
            .setMessage("정말로 앱을 종료하시겠습니까?")
            .setPositiveButton("종료") { _, _ ->
                activity?.let {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    it.startActivity(intent)
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                showExitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun Get_Community_Info() {
        Firebase.database.getReference(DBKey.Community_Key)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 저장합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })

// 찜 목록이 변경될 때마다 로컬 데이터를 업데이트합니다.
        Firebase.database.getReference(DBKey.Community_Key)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 업데이트합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })
    }

    private fun Get_MyWishlist_Info() {
        val myid = Firebase.auth.currentUser?.uid ?: ""
        Firebase.database.getReference(DBKey.My_Wishlist).child(myid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 저장합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })

// 찜 목록이 변경될 때마다 로컬 데이터를 업데이트합니다.
        Firebase.database.getReference(DBKey.My_Wishlist).child(myid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 업데이트합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })
    }

    private fun Get_MyInfo() {
        val myid = Firebase.auth.currentUser?.uid ?: ""
        Firebase.database.getReference(DBKey.DB_USERS).child(myid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 저장합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })

// 찜 목록이 변경될 때마다 로컬 데이터를 업데이트합니다.
        Firebase.database.getReference(DBKey.DB_USERS).child(myid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 업데이트합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })
    }

    private fun Get_TripInfo() {
        Firebase.database.getReference(DBKey.Trip_Info)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 저장합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })

// 찜 목록이 변경될 때마다 로컬 데이터를 업데이트합니다.
        Firebase.database.getReference(DBKey.Trip_Info)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 로컬에 찜 목록을 업데이트합니다.
                    //val wishlist = dataSnapshot.children.map { it.key }
                    // TODO: 이 데이터를 사용하여 UI를 업데이트합니다.
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 쿼리 실행에 실패했을 때의 동작을 수행합니다.
                    Log.e("FirebaseError", "Query canceled: $databaseError")
                }
            })
    }
   private fun getDataFirebase(data){

   }


}