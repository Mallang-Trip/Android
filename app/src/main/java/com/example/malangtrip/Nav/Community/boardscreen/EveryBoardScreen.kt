package com.example.malangtrip.Nav.Community.boardscreen

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.Nav.Community.readcommunity.GoToBoard
import com.example.malangtrip.Nav.Community.writecommunity.Write_Text
import com.example.malangtrip.R
import com.example.malangtrip.databinding.FragmentEveryBoardWritingBinding
import com.example.malangtrip.key.DBKey
import com.example.malangtrip.key.DBKey.Companion.Community_Key
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//전체게시판
class EveryBoardScreen : Fragment() {
    private var _binding: FragmentEveryBoardWritingBinding? = null
    private val binding get() = _binding!!
    private val everyBoardList = mutableListOf<CommunityItem>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var everyBoardAdapter : BoardAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentEveryBoardWritingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
       
        //게시판 어댑터 생성
        createAdapter()
        //데이터 받아오기
        getEveryBoardInfo()
        return root


    }
    private fun createAdapter()
    {
        everyBoardAdapter = BoardAdapter(everyBoardList)
        binding.lvBoard.adapter = everyBoardAdapter
        binding.lvBoard.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, GoToBoard::class.java)
            intent.putExtra("name",everyBoardList[position].userName)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }
    }
    private fun getEveryBoardInfo()
    {
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                everyBoardList.clear()
                if (snapshot.exists()) {
                    for (WriteSnapshot in snapshot.children) {


                        val item = WriteSnapshot.getValue(CommunityItem::class.java)

                        everyBoardList.add(item!!)
                        boardKeyList.add(WriteSnapshot.key.toString())
                    }
                    boardKeyList.reverse()
                    everyBoardList.reverse()
                    everyBoardAdapter.notifyDataSetChanged()
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
        Firebase.database(DBKey.DB_URL).reference.child(Community_Key).addValueEventListener(postListener)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.community_write, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //글쓰기로 이동, 글 재생성
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.write_btn -> {
                // 버튼이 클릭되었을 때의 동작을 여기에 코딩합니다.
                startActivity(Intent(context, Write_Text::class.java))
                true
            }
            R.id.reset_button->
            {
                getEveryBoardInfo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}