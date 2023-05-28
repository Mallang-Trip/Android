package com.example.malangtrip.Nav.Myinfo.Resister_Driver

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.example.malangtrip.Nav.Common_Function_Fragment
import com.example.malangtrip.Nav.Community.Write_Community.Fix_Board
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoRegisterDriverBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

//드라이버로 등록하기
class Main_Resister_Driver : Fragment(){
    private var _binding: NMyinfoRegisterDriverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var photo_Check = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoRegisterDriverBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //액션바 활성화 및 이름 변경후 뒤로가기 버튼 활성화
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "드라이버로 등록하기"
        //메뉴 사용 활성화
        setHasOptionsMenu(true)

        // 뒤로가기 버튼 처리 이전 프래그먼트로 감
        root.isFocusableInTouchMode = true
        root.requestFocus()
        root.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().supportFragmentManager.popBackStack()
                true
            } else {
                false
            }
        }
        //사진 선택
        binding.selectBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }
        binding.completeBtn.setOnClickListener {


            if(photo_Check==true)
            {

            }
        }
        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== AppCompatActivity.RESULT_OK && requestCode ==100)
        {
            photo_Check = true
            binding.uploadBtn.setImageURI( data?.data)
        }
    }
    private fun upload_Image(key : String){

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        val imageView = binding.uploadBtn
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }
    //상단 뒤로가기 버튼 눌렀을 때 이전 프래그먼트로
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
    private fun show_dialog()
    {
        val DialogView = LayoutInflater.from(context).inflate(R.layout.n_myinfo_register_driver_dialog,null)
        val builder = context?.let {
            AlertDialog.Builder(it)
                .setView(DialogView)
                .setTitle("지역선택")
        }

        val alterDialog = builder?.show()
        alterDialog?.findViewById<Button>(R.id.cancle_Btn)?.setOnClickListener {



        }
        alterDialog?.findViewById<Button>(R.id.check_Btn)?.setOnClickListener {

        }
    }
}