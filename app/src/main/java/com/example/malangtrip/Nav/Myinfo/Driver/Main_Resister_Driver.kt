package com.example.malangtrip.Nav.Myinfo.Driver

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.malangtrip.login.User_Info
import com.example.malangtrip.Nav.Myinfo.Driver.Driver_Info.Driver_Info
import com.example.malangtrip.R
import com.example.malangtrip.databinding.NMyinfoRegisterDriverBinding
import com.example.malangtrip.login.DBKey
import com.google.firebase.auth.ktx.auth
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
    //private lateinit var radioGroup: RadioGroup
    private var checkedRadioButtonId: Int = -1
    private var driverLocal:String?=null
    private var name: String? = null
    private lateinit var description:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NMyinfoRegisterDriverBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val curruntId = Firebase.auth.currentUser?.uid ?: "" // 현재 유저 아이디 가져오기
        val mydb = Firebase.database.reference.child(DBKey.DB_USERS).child(curruntId)//내 정보 접근
        mydb.get().addOnSuccessListener {
            val myinfo = it.getValue(User_Info::class.java)?: return@addOnSuccessListener
            name = myinfo.nickname.toString()
            description = myinfo.description.toString()
        }
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
        binding.uploadBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }
        binding.cancleBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.completeBtn.setOnClickListener {
            val cartype = binding.inputCarNum.text.toString().trim()
            val license_Plate = binding.inputNum.text.toString().trim()
            val available_Num = binding.inputAvailNum.text.toString().trim()

            if (cartype.isEmpty() || license_Plate.isEmpty() || available_Num.isEmpty() || driverLocal == null) {
                Toast.makeText(context,"입력 안 된 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 나머지 코드들을 이곳에 배치하세요.


                if (photo_Check == true) {
                    upload_Image(curruntId)
                }
                if (driverLocal == "jeju") {
                    Firebase.database(DBKey.DB_URL).reference.child(DBKey.Driver)
                        .child(curruntId)
                        .setValue(
                            Driver_Info(
                                "jeju",
                                name,
                                description,
                                cartype,
                                license_Plate,
                                available_Num,
                                null
                            )
                        )
                }
                if (driverLocal == "ulleung") {
                    Firebase.database(DBKey.DB_URL).reference.child(DBKey.Driver)
                        .child(curruntId)
                        .setValue(
                            Driver_Info(
                                "ulleung",
                                name,
                                description,
                                cartype,
                                license_Plate,
                                available_Num
                            )
                        )
                }
                requireActivity().supportFragmentManager.popBackStack()

            }
        }
        binding.selectLocalBtn.setOnClickListener {
            show_dialog()
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
//        radioGroup = binding.root.findViewById(R.id.localGroup)
//        radioGroup?.clearCheck()
//        checkedRadioButtonId = radioGroup.checkedRadioButtonId


        val DialogView = LayoutInflater.from(context).inflate(R.layout.n_myinfo_register_driver_dialog,null)
        val builder = context?.let {
            AlertDialog.Builder(it)
                .setView(DialogView)
                .setTitle("지역선택")
        }

        val alterDialog = builder?.show()
        val radioGroup = alterDialog?.findViewById<RadioGroup>(R.id.localGroup)
        radioGroup?.clearCheck()

        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            checkedRadioButtonId = checkedId
        }
        alterDialog?.findViewById<Button>(R.id.cancle_Btn)?.setOnClickListener {

            alterDialog.dismiss()

        }
        alterDialog?.findViewById<Button>(R.id.check_Btn)?.setOnClickListener {
            when (checkedRadioButtonId) {
                R.id.jeju -> {
                    driverLocal = "jeju"
                }
                R.id.ulleung -> {
                    driverLocal = "ulleung"
                }

            }
            driverLocal?.let { it1 -> Log.d("지역", it1) }
            //Log.d("지역",driverLocal)
            alterDialog.dismiss()
        }
    }
}