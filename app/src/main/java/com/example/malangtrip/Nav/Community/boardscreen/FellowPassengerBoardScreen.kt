package com.example.malangtrip.Nav.Community.boardscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.Nav.Community.readcommunity.GoToBoard
import com.example.malangtrip.Nav.Community.Write_Community.Write_Text
import com.example.malangtrip.R
import com.example.malangtrip.databinding.FragmentFellowPassengerBoardWritingBinding
import com.example.malangtrip.key.DBKey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FellowPassengerBoardScreen : Fragment(){
    private var _binding: FragmentFellowPassengerBoardWritingBinding? = null
    private val binding get() = _binding!!
    private val fellowPassengerBoardList = mutableListOf<CommunityItem>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var fellowPassengerBoardAdapter : BoardAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFellowPassengerBoardWritingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)


        //어뎁터 생성
        createAdapter()
        //데이터 받아오기
        getFellowPassengerBoardinfo()
        return root


    }
    private fun createAdapter()
    {
        fellowPassengerBoardAdapter = BoardAdapter(fellowPassengerBoardList)
        binding.lvBoard.adapter = fellowPassengerBoardAdapter
        binding.lvBoard.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, GoToBoard::class.java)
            intent.putExtra("name",fellowPassengerBoardList[position].userName)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }
    }
    private fun getFellowPassengerBoardinfo()
    {
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fellowPassengerBoardList.clear()
                if (snapshot.exists()) {
                    for (WriteSnapshot in snapshot.children) {


                        val item = WriteSnapshot.getValue(CommunityItem::class.java)
                        if (item?.boardType == "passenger") {
                            fellowPassengerBoardList.add(item!!)
                            boardKeyList.add(WriteSnapshot.key.toString())
                        }
                    }
                    fellowPassengerBoardList.reverse()
                    fellowPassengerBoardList.reverse()
                    fellowPassengerBoardAdapter.notifyDataSetChanged()
                    Log.d("dffff",fellowPassengerBoardList.toString())
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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.community_write, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //글쓰기로 이동, 글 재생성
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.write_btn -> {
                startActivity(Intent(context, Write_Text::class.java))
                true
            }
            R.id.reset_button->
            {
                getFellowPassengerBoardinfo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}