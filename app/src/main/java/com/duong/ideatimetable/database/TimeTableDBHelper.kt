package com.duong.ideatimetable.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.duong.ideatimetable.entity.*

class TimeTableDBHelper(
    context: Context?,
    name: String = "timetable.db",
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        val queryAnh = "create table if not exists anh(\n" +
                "id text,\n" +
                "url text\n" +
                ")"
        val queryMau = "create table if not exists mau(\n" +
                "id text,\n" +
                "ma text\n" +
                ")"
        val queryThongBao = "create table if not exists thongbao(\n" +
                "id text,\n" +
                "laptheongay text,\n" +
                "ngaylap text,\n" +
                "ambao text,\n" +
                "giobatdau text,\n" +
                "gioketthuc text,\n" +
                "lapambao text\n" +
                ")"
        val queryGhiChu = "create table if not exists ghichu (\n" +
                "id text,\n" +
                "tieude text,\n" +
                "phude text,\n" +
                "noidung text,\n" +
                "ngaytao text,\n" +
                "user_id text,\n" +
                "thongbao_id text,\n" +
                "anh_id text\n" +
                ")"
        val queryNhiemVu = "create table if not exists nhiemvu(\n" +
                "id text,\n" +
                "noidung text,\n" +
                "hoanthanh text,\n" +
                "ghichu_id text\n" +
                ")"
        db.execSQL(queryThongBao)
        db.execSQL(queryAnh)
        db.execSQL(queryMau)
        db.execSQL(queryGhiChu)
        db.execSQL(queryNhiemVu)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("drop table if exists nhiemvu")
        db.execSQL("drop table if exists ghichu")
        db.execSQL("drop table if exists mau")
        db.execSQL("drop table if exists anh")
        db.execSQL("drop table if exists thongbao")
        onCreate(db)
    }
    fun openOrCreateDB(){
        val db = SQLiteDatabase.openOrCreateDatabase("timetable.db", null)
        onCreate(db)
    }
    fun taoGhiChu(ghiChu: GhiChu){
        val values = ContentValues()
        values.put("id", ghiChu.id)
        values.put("tieude", ghiChu.tieuDe)
        values.put("phude", ghiChu.phuDe)
        values.put("noidung", ghiChu.noiDung)
        values.put("ngaytao", ghiChu.ngayTao)
        values.put("user_id", ghiChu.userId)
        values.put("thongbao_id", ghiChu.thongBaoId)
        values.put("anh_id", ghiChu.anhId)
        val db = this.writableDatabase
        db.insert("ghichu", null, values)
        db.close()
    }
    fun taoNhiemVu(nhiemVu: NhiemVu){
        val values = ContentValues()
        values.put("id", nhiemVu.id)
        values.put("noidung", nhiemVu.noiDung)
        values.put("hoanthanh", nhiemVu.hoanThanh)
        values.put("ghichu_id", nhiemVu.ghiChuId)
        val db = this.writableDatabase
        db.insert("nhiemvu", null, values)
        db.close()
    }
    fun taoThongBao(thongBao: ThongBao){
        val values = ContentValues()
        values.put("id", thongBao.id)
        values.put("laptheongay", thongBao.lapTheoNgay)
        values.put("ambao", thongBao.amBao)
        values.put("giobatdau", thongBao.gioBatDau)
        values.put("gioketthuc", thongBao.gioKetThuc)
        values.put("lapambao", thongBao.lapAmBao)
        val db = this.writableDatabase
        db.insert("thongbao", null, values)
        db.close()
    }
    fun taoAnh(anh: Anh){
        val values = ContentValues()
        values.put("id", anh.id)
        values.put("url", anh.url)
        val db = this.writableDatabase
        db.insert("anh", null, values)
        db.close()
    }
    fun taoMau(mau: Mau){
        val values = ContentValues()
        values.put("id", mau.id)
        values.put("ma", mau.ma)
        val db = this.writableDatabase
        db.insert("mau", null, values)
        db.close()
    }
    fun rawQuery(query: String): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }
    fun execSQL(query: String){
        val db = this.writableDatabase
        db.execSQL(query)
    }
}