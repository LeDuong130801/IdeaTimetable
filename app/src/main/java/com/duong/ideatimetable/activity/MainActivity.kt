package com.duong.ideatimetable.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.duong.ideatimetable.R
import com.duong.ideatimetable.adapter.GhiChuAdapter
import com.duong.ideatimetable.database.TimeTableDBHelper
import com.duong.ideatimetable.entity.GhiChu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        khoiTaoSuKien()
        taiGhiChu()
    }
    private fun khoiTaoSuKien(){
        themGhiChuMoi.setOnClickListener {

            val intent = Intent(this, CreateNoteScreen::class.java)
            startActivity(intent)
        }
        iconTimKiem.setOnClickListener {
            timKiem()
        }
        nhapNoiDungTimKiem.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                timKiem()
                true
            }
            else
            false
        })
    }
    private fun timKiem(){
        val filterValue = nhapNoiDungTimKiem.text
        Toast.makeText(this.applicationContext, filterValue, Toast.LENGTH_LONG).show()
    }
    private fun taiGhiChu(){
//        rcvGhiChu.layoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
        val array = mutableListOf<GhiChu>()
        val db = TimeTableDBHelper(this, "timetable.db", null, 1)
        val cursor = db.rawQuery("select * from ghichu")
        if(cursor!=null)
        if(cursor.moveToFirst()){
            do{
                val id = db.getCString(cursor, "id")
                val tieuDe = db.getCString(cursor, "tieude")
                val phuDe = db.getCString(cursor, "phude")
                val noiDung = db.getCString(cursor, "noidung")
                val ngayTao = db.getCString(cursor, "ngaytap")
                val userId = db.getCString(cursor, "user_id")
                val thongBaoId = db.getCString(cursor, "thongbao_id")
                val anhId = db.getCString(cursor, "anh_id")
                val mauId = db.getCString(cursor, "mau_id")
                val trangThai = db.getCString(cursor, "trangthai")
                val ghiChu = GhiChu(id, tieuDe, phuDe, noiDung, ngayTao, userId, thongBaoId, anhId, mauId, trangThai)
                array.add(ghiChu)
            }
                while (cursor.moveToNext());
        }
        rcvGhiChu.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rcvGhiChu.adapter = GhiChuAdapter(this.applicationContext,array)
    }
}