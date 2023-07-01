package com.example.malangtrip.nav.community.boardscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.nav.community.readcommunity.GoToBoard
import com.example.malangtrip.databinding.FragmentMyTextListBinding
import com.example.malangtrip.key.DBKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MyTextList : Fragment(){
    private var _binding: FragmentMyTextListBinding? = null
    private val binding get() = _binding!!
    private val everyBoardList = mutableListOf<CommunityItem>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var myBoardadapter : MyBoardAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentMyTextListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "나의 작성글 내역"



//        binding.writeBtn.setOnClickListener {
//            startActivity(Intent(context, Write_Text::class.java))
//        }
        getEveryBoardInfo()
        return root


    }
    private fun createAdapter()
    {
            myBoardadapter = MyBoardAdapter(everyBoardList){communityItem->
            val intent = Intent(context, GoToBoard::class.java)
            intent.putExtra("name",communityItem.userName)
            intent.putExtra("key", communityItem.communityKey)
            startActivity(intent)
       }
        binding.rvMyText.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myBoardadapter
        }

//        binding.rvMyText.setOnItemClickListener { ->
//            val intent = Intent(context, GoToBoard::class.java)
////            intent.putExtra("title",Every_Board_List[position].title)
////            intent.putExtra("time",Every_Board_List[position].time)
////            intent.putExtra("content",Every_Board_List[position].content)
//            intent.putExtra("name",everyBoardList[position].userName)
//            intent.putExtra("key", boardKeyList[position])
//
//            startActivity(intent)
//        }
    }
    private fun getEveryBoardInfo()
    {
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                everyBoardList.clear()
                if (snapshot.exists()) {
                    for (WriteSnapshot in snapshot.children) {


                        val item = WriteSnapshot.getValue(CommunityItem::class.java)
                        if (item?.userId == Firebase.auth.currentUser?.uid ?: "") {
                            everyBoardList.add(item!!)
                            boardKeyList.add(WriteSnapshot.key.toString())
                        }
                        //chatListAdapter.submitList(chatRoomList)
                        //Every_Board_List.add(item!!)


                    }
                    boardKeyList.reverse()
                    everyBoardList.reverse()

                    createAdapter()
                    myBoardadapter.notifyDataSetChanged()
                    Log.d("dffff",everyBoardList.toString())
                }
                else
                {
                    // Users 경로에 데이터가 없음

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 쿼리 취소 시
            }
        }
        Firebase.database(DBKey.DB_URL).reference.child(DBKey.Community_Key).addValueEventListener(postListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //requireActivity().onBackPressed()
                requireActivity().supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}