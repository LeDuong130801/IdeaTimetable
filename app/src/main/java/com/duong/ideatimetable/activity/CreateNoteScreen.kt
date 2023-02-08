package com.duong.ideatimetable.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.duong.ideatimetable.R
import com.duong.ideatimetable.database.TimeTableDBHelper
import com.duong.ideatimetable.entity.GhiChu
import kotlinx.android.synthetic.main.activity_create_note_screen.*
import java.io.File
import java.time.LocalDate
import java.util.*


class CreateNoteScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note_screen)
        khoiTaoSuKien()
    }
    private fun khoiTaoSuKien(){
        hoanThanhTaoGhiChu.setOnClickListener{
            val db = TimeTableDBHelper(this, "timetable.db", null, 1)
            val tieuDe = enterTitle.text.toString()
            val phuDe = enterSubtitle.text.toString()
            val noiDung = enterContent.text.toString()
            val ngayTao = LocalDate.now().toString()
            val id = db.getAnUniqueId("ghichu")
            val ghiChu = GhiChu(id, tieuDe, phuDe, noiDung, ngayTao, "0", "0", "0", "0")
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
    }

    private fun chonAnhTuThuVien() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Chọn ảnh"), 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK && data != null){
            if(requestCode == 100){
                val selectedImg = data.data
                File(selectedImg.toString()).copyTo(File(Environment.getExternalStorageDirectory().absolutePath+"err."+data.type), overwrite = true, DEFAULT_BUFFER_SIZE);
            }
        }
    }
}