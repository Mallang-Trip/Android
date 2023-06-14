package com.example.malangtrip.Nav.Community.Read_Community

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.malangtrip.Nav.Community.Comment.Comment_Adapter
import com.example.malangtrip.key.CommentItem
import com.example.malangtrip.Nav.Community.CommunityAuth
import com.example.malangtrip.key.CommunityItem
import com.example.malangtrip.Nav.Community.Write_Community.Fix_Board
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NCommunityTextBinding
import com.example.malangtrip.key.DBKey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class Go_To_Board : AppCompatActivity(){
    private lateinit var binding: NCommunityTextBinding


    private lateinit var key: String
    var menu_Check = false
    //사진 여러장
    //private lateinit var imageAdapter: Board_Image_Adapter
//    private var imageCount by Delegates.notNull<Int>()
//    private val imageUrls = mutableListOf<String>()
    private  lateinit var name :String
    private val commentDataList = mutableListOf<CommentItem>()
    private lateinit var commentAdapter: Comment_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =NCommunityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)


        key = intent.getStringExtra("key").toString()
        name  = intent.getStringExtra("name").toString()

        Log.d("게시판 키값",key)
    //사진 여러장
//        imageAdapter = Board_Image_Adapter(imageUrls)
//        binding.imageItems.adapter = imageAdapter
//        binding.imageItems.layoutManager = LinearLayoutManager(this)


        getBoardData(key)
        getImageData(key)
        binding.commentBtn.setOnClickListener {
            val comment = binding.commentArea.text.toString()
            if(comment==null)
            {
                Toast.makeText(this, "아무 것도 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            insertComment(key)
            Toast.makeText(this, "댓글 입력 완료", Toast.LENGTH_SHORT).show()
//            binding.commentLV.post {
//                binding.commentLV.setSelection(commentAdapter.count - 1)
//            }
            // 키보드 내리기
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.commentArea.windowToken, 0)

            Handler(Looper.getMainLooper()).postDelayed({
                binding.forDown.fullScroll(View.FOCUS_DOWN)
            }, 300)
        }
       // getCommentData(key)

        //리스트뷰
        commentAdapter = Comment_Adapter(commentDataList)
        binding.commentLV.adapter = commentAdapter
        getCommentData(key)
        //리사이클러뷰
//        commentAdapter = Comment_Adapter(commentDataList)
//        binding.commentLV.layoutManager = LinearLayoutManager(this)
//        binding.commentLV.adapter = commentAdapter
        //키보드 나타났을 때 채팅창 위로 올림



    }

    //사진 여러장
//   private fun getImageData(key : String)
//    {
//        val storageReference = Firebase.storage.reference
//         imageCount = intent.getIntExtra("imageCount", 0) // Assuming you pass the image count from the previous activity
//
//        for (i in 0 until imageCount) {
//            val fileName = "$key-$i.png"
//            val imageRef = storageReference.child(fileName)
//            imageRef.downloadUrl.addOnSuccessListener { uri ->
//                imageUrls.add(uri.toString())
//                imageAdapter.notifyDataSetChanged()
//
//            }.addOnFailureListener {
//                // Handle any errors
//            }
//        }
//
//
//    }
    //단일 사진
private fun getImageData(key : String)
{
    /// Reference to an image file in Cloud Storage
    val storageReference = Firebase.storage.reference.child(key + ".png")

    // ImageView in your Activity
    val imageViewFromFB = binding.imageArea
    Log.d("123",key + ".png")
    storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
        if(task.isSuccessful) {

            Glide.with(this)
                .load(task.result)
                .into(imageViewFromFB)


        } else {
                binding.imageArea.isVisible = false
        }
    })
}

    fun insertComment(key : String){
    val boardDb = Firebase.database.reference.child(DBKey.Community_Key).child(key)
    boardDb.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val boardInfo = dataSnapshot.getValue(CommunityItem::class.java)
            val commentNum = boardInfo?.commentNum ?: 0
            boardDb.child("commentNum").setValue(commentNum + 1)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // 오류 처리
        }
    })

        val myid = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
        // comment
        //   - BoardKey
        //        - CommentKey
        //            - CommentData
        //            - CommentData
        //            - CommentData
        Firebase.database.getReference("Users").child(myid).child("nickname")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name = dataSnapshot.getValue(String::class.java)
                    if (name != null) {
                        // 여기서 검색된 이름을 사용합니다
                        Firebase.database.getReference("Comment")
                            .child(key)
                            .push()
                            .setValue(
                                CommentItem(
                                    name,
                                    binding.commentArea.text.toString(),
                                    CommunityAuth.getTime()

                                )
                            )
                        binding.commentArea.setText("")
                        getCommentData(key)
                        //binding.commentLV.setSelection(commentAdapter.count - 1)

                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 여기서 잠재적인 오류를 처리합니다
                }
            })



    }

    private fun getBoardData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(CommunityItem::class.java)


                        //binding.boardTitle.text = dataModel!!.title
                    supportActionBar?.apply {
                        title = dataModel!!.title
                        setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 표시
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.my_home_back)
                    }
                    binding.boardContent.text = dataModel!!.content
                    binding.boardTime.text = dataModel!!.time
                    binding.boardUserName.text = dataModel!!.userName
                    val myid = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
                    val text_id = dataModel.userId
                    if(myid==text_id)
                    {

                        Log.d("나 자신임",myid)
                        menu_Check=true
                        //invalidateOptionsMenu()
                    }
                    else{
                        Log.d("나 자신이 아님",myid)
                    }

                } catch (e: Exception) {

                    Log.d(TAG, "삭제완료")

                }





            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

    Firebase.database(DBKey.DB_URL).reference.child(DBKey.Community_Key).child(key).addValueEventListener(postListener)
    }

    override fun onResume() {
        super.onResume()
        getImageData(key)
    }
    fun getCommentData(key:String)
    {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    commentDataList.clear()
                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(CommentItem::class.java)
                    commentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()
                // 스크롤을 마지막 댓글로 이동



                updateListViewHeight()

            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }


        //Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
        Firebase.database.reference.child(DBKey.Comment_Key).child(key).addValueEventListener(postListener)


    }
    fun updateListViewHeight() {
        val totalHeight = commentDataList.size *81
        val params = binding.commentLV.layoutParams
        params.height = totalHeight.dpToPx()
        binding.commentLV.layoutParams = params
        binding.commentLV.requestLayout()


    }

    fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //requireActivity().onBackPressed()
                finish()
                true
            }
            R.id.fix_Btn->{
                val intent = Intent(this, Fix_Board::class.java)
                intent.putExtra("key", key)
                intent.putExtra("name", name)
                startActivity(intent)
                finish()
                true
            }
            R.id.delete_Btn->{
                Firebase.database.getReference("EveryCommunity").child(key).removeValue()
                Toast.makeText(this,"글이 삭제되었습니다",Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if(menu_Check) {
            menuInflater.inflate(R.menu.fix_board, menu)
        }
        return true
    }

}