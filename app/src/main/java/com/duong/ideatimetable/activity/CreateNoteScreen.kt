package com.duong.ideatimetable.activity

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.duong.ideatimetable.R
import com.duong.ideatimetable.adapter.GhiChuAdapter
import com.duong.ideatimetable.adapter.NhiemVuAdapter
import com.duong.ideatimetable.database.TimeTableDBHelper
import com.duong.ideatimetable.entity.Anh
import com.duong.ideatimetable.entity.GhiChu
import com.duong.ideatimetable.entity.NhiemVu
import kotlinx.android.synthetic.main.activity_create_note_screen.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.util.*


class CreateNoteScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note_screen)
        val db = TimeTableDBHelper(this.applicationContext, "timetable.db", null, 1)
        db.clearTempNhiemVu()
        db.close()
        khoiTaoSuKien()
    }
    lateinit var listNhiemVu: MutableList<NhiemVu>
    var anhId = "0"
    private fun khoiTaoSuKien(){
        hoanThanhTaoGhiChu.setOnClickListener{
            val db = TimeTableDBHelper(this, "timetable.db", null, 1)
            val tieuDe = enterTitle.text.toString()
            val phuDe = enterSubtitle.text.toString()
            val noiDung = enterContent.text.toString()
            val ngayTao = LocalDate.now().toString()
            val id = db.getAnUniqueId("ghichu")
            val ghiChu = GhiChu(id, tieuDe, phuDe, noiDung, ngayTao, "0", "0", anhId, "0")
            db.taoGhiChu(ghiChu)
            db.close()
            this.finish()
        }
        goBack.setOnClickListener{
            this.finish()
        }
        themAnh.setOnClickListener{
            chonAnhTuThuVien()
        }
        themNhiemVu.setOnClickListener {
            listNhiemVu.add(NhiemVu("0","",false,"0" ))
            rcvNhiemVu.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rcvNhiemVu.adapter = NhiemVuAdapter(this.applicationContext, listNhiemVu)
        }
    }

    private fun chonAnhTuThuVien() {
//        val i = Intent()
//        i.type = "image/*"
//        i.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(i, "Chọn ảnh"), 100)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        pickImage.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK && data != null){
            if(requestCode == 100){
                var selectedImg = data.data
//                writeData(selectedImg)
            }
        }
    }
    private fun encodeImage(bitmap: Bitmap): ByteArray {
        val previewWidth = 600
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap: Bitmap =
            Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
//        return Base64.getEncoder().encodeToString(bytes)
    }
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val imageUri = it.data?.data
                try {
                    if(imageUri!=null){
                        val inputSteam = imageUri.let { itn -> contentResolver.openInputStream(itn) }
//                        val options = BitmapFactory.Options()
//                        options.inSampleSize = 2
//                        options.inJustDecodeBounds = true
//                        val bitmap: Bitmap? = BitmapFactory.decodeStream(inputSteam, Rect(),options)
                        val bitmap: Bitmap? = BitmapFactory.decodeStream(inputSteam)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                        anhtest.setImageBitmap(bitmap)
                        anhtest.isGone = false
                        anhtest.isVisible = true
                        val db = TimeTableDBHelper(this, "timetable.db", null, 1)
                        anhId = db.taoVaLayIdAnh(getPathFromUri(imageUri))
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    fun getPathFromUri(uri: Uri): String{
        var filePath: String
        var cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor == null){
            if(uri.path!= null){
                filePath = uri.path!!
            }
            else filePath = "0"
        }
        else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            if(index > 0){
                filePath = cursor.getString(index)
            }
            else filePath = "0"
        }
        return filePath
    }
}