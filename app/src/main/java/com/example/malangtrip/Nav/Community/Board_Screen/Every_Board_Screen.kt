package com.example.malangtrip.Nav.Community.Board_Screen

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.malangtrip.Key.CommunityItem
import com.example.malangtrip.Nav.Community.Read_Community.Go_To_Board
import com.example.malangtrip.Nav.Community.Write_Community.Write_Text
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NCommunityEveryBoardWriteBinding
import com.example.malangtrip.Key.DBKey
import com.example.malangtrip.Key.DBKey.Companion.Community_Key
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Every_Board_Screen : Fragment() {
    private var _binding: NCommunityEveryBoardWriteBinding? = null
    private val binding get() = _binding!!
    private val Every_Board_List = mutableListOf<CommunityItem>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var Every_adapter : Board_Adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NCommunityEveryBoardWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //Get_Every_Board_info()

        Every_adapter = Board_Adapter(Every_Board_List)
        binding.freeBoard.adapter = Every_adapter
        binding.freeBoard.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, Go_To_Board::class.java)
//            intent.putExtra("title",Every_Board_List[position].title)
//            intent.putExtra("time",Every_Board_List[position].time)
//            intent.putExtra("content",Every_Board_List[position].content)
            intent.putExtra("name",Every_Board_List[position].userName)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

//        binding.writeBtn.setOnClickListener {
//            startActivity(Intent(context, Write_Text::class.java))
//        }
        Get_Every_Board_info()
        return root


    }

    private fun Get_Every_Board_info()
    {
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Every_Board_List.clear()
                if (snapshot.exists()) {
                    for (WriteSnapshot in snapshot.children) {


                        val item = WriteSnapshot.getValue(CommunityItem::class.java)

                        Every_Board_List.add(item!!)
                        boardKeyList.add(WriteSnapshot.key.toString())

                    }
                    boardKeyList.reverse()
                    Every_Board_List.reverse()
                    Every_adapter.notifyDataSetChanged()
                    //Log.d("dffff",Every_Board_List.toString())
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.write_btn -> {
                // 버튼이 클릭되었을 때의 동작을 여기에 코딩합니다.
                startActivity(Intent(context, Write_Text::class.java))
                true
            }
            R.id.reset_button->
            {
                Get_Every_Board_info()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}