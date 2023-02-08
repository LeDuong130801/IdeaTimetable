package com.duong.ideatimetable.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.duong.ideatimetable.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        khoiTaoSuKien()
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
}