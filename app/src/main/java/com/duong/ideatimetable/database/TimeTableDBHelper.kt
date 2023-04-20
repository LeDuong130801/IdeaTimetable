package com.duong.ideatimetable.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.duong.ideatimetable.entity.*
import kotlin.math.abs
import kotlin.random.Random

class TimeTableDBHelper(
    context: Context?,
    name: String = "timetable.db",
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        val queryAnh = "create table if not exists anh(\n" +
                "id text,\n" +
                "url text,\n" +
                "trangthai text)"
        val queryMau = "create table if not exists mau(\n" +
                "id text,\n" +
                "ma text\n,\n" +
                "trangthai text)"
        val queryThongBao = "create table if not exists thongbao(\n" +
                "id text,\n" +
                "laptheongay text,\n" +
                "ngaylap text,\n" +
                "ambao text,\n" +
                "giobatdau text,\n" +
                "gioketthuc text,\n" +
                "lapambao text,\n" +
                "trangthai text)"
        val queryGhiChu = "create table if not exists ghichu (\n" +
                "id text,\n" +
                "tieude text,\n" +
                "phude text,\n" +
                "noidung text,\n" +
                "ngaytao text,\n" +
                "user_id text,\n" +
                "thongbao_id text,\n" +
                "anh_id text,\n" +
                "mau_id text,\n" +
                "trangthai text)"
        val queryNhiemVu = "create table if not exists nhiemvu(\n" +
                "id text,\n" +
                "noidung text,\n" +
                "hoanthanh text,\n" +
                "ghichu_id text,\n" +
                "trangthai text)"
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
        val str = "insert into ghichu(id, tieude, phude, noidung, ngaytao, user_id, thongbao_id, anh_id, mau_id, trangthai) values (\"" +
                ghiChu.id+"\",\""+
                ghiChu.tieuDe+"\",\""+
                ghiChu.phuDe+"\",\""+
                ghiChu.noiDung+"\",\""+
                ghiChu.ngayTao+"\",\""+
                ghiChu.userId+"\",\""+
                ghiChu.thongBaoId+"\",\""+
                ghiChu.anhId+"\",\""+
                ghiChu.mauNenId+"\",\"0\")"
        execSQL(str)
    }
    fun taoNhiemVu(nhiemVu: NhiemVu){
        val id = nhiemVu.id
        val noidung = nhiemVu.noiDung
        val hoanthanh = if (nhiemVu.hoanThanh){
            "1"
        } else "0"
        val ghichuId = nhiemVu.ghiChuId
        val strSQL = "insert into nhiemvu(id, noidung, hoanthanh, ghichu_id, trangthai) values(" +
                "\"$id\","+
                "\"$noidung\","+
                "$hoanthanh,"+
                "\"$ghichuId\","+
                "\"0\")"
        execSQL(strSQL)
    }
    fun taoThongBao(thongBao: ThongBao){
        val id = thongBao.id
        val lapTheoNgay = if (thongBao.lapTheoNgay){
            "1"
        } else "0"
        val ngayLap = thongBao.ngayLap
        val amBao = if (thongBao.amBao){
            "1"
        } else "0"
        val gioBatDau = thongBao.gioBatDau
        val gioKetThuc = thongBao.gioKetThuc
        val lapAmBao = thongBao.lapAmBao
        val strSQL = "insert into thongbao(id, laptheongay, ngaylap, ambao, giobatdau, gioketthuc, lapambao, trangthai) values(" +
                "\"$id\","+
                "\"$lapTheoNgay\","+
                "$ngayLap,"+
                "\"$amBao\","+
                "\"$gioBatDau\","+
                "\"$gioKetThuc\","+
                "\"$lapAmBao\","+
                "\"0\")"
        execSQL(strSQL)
    }
    fun taoAnh(anh: Anh){
        val id = anh.id
        val url = anh.url
        val strSQL = "insert into anh(id, url, trangthai) values(" +
                "\"$id\","+
                "\"$url\","+
                "\"0\")"
        execSQL(strSQL)
    }
    fun taoMau(mau: Mau){
        val id = mau.id
        val ma = mau.ma
        val strSQL = "insert into mau(id, ma, trangthai) values(" +
                "\"$id\","+
                "\"$ma\","+
                "\"0\")"
        execSQL(strSQL)
    }
    fun rawQuery(query: String): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }
    fun execSQL(query: String){
        val db = this.writableDatabase
        db.execSQL(query)
    }
    fun getAnUniqueId(tableName: String): String{
        val random = Random
        var check: Boolean
        var randomstr: String
        do {
            check = false
            val strleng = abs(random.nextInt()%15+7)
            val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
            randomstr =  (1..strleng)
                .map { charset.random() }
                .joinToString("")
            val a = rawQuery("select * from "+tableName+" where id = '"+randomstr+"'")
            if (a?.moveToFirst()==true){
                check = true
            }
        }
            while (check);
        return randomstr
    }
    fun getCString(cursor: Cursor, name: String): String{
        val index = cursor.getColumnIndex(name)
        if(index ==-1){
            return ""
        }
        return cursor.getString(index)
    }
    fun taoVaLayIdAnh(url: String): String{
        val id = getAnUniqueId("anh")
        taoAnh(Anh(id, url, "0"))
        return id
    }
    fun xoaDuLieuNhiemVuTamThoi(){
        xoaDuLieuTam("nhiemvu")
    }
    fun xoaDuLieuGhiChuTamThoi(){
        xoaDuLieuTam("ghichu")
    }
    fun xoaDuLieuMauTamThoi(){
        xoaDuLieuTam("mau")
    }
    fun xoaDuLieuAnhTamThoi(){
        xoaDuLieuTam("anh")
    }
     fun xoaDuLieuThongBaoTamThoi(){
        xoaDuLieuTam("thongbao")
    }
    private fun xoaDuLieuTam(tableName: String){
        execSQL("delete from $tableName where trangthai = \"0\"")
    }
    fun luuDuLieuTam(tableName: String, listId: MutableList<String>){
        for(id in listId){
            execSQL("update $tableName set trangthai = \"1\" where id = \"$id\"")
        }
        Thread.sleep(2000)
        xoaDuLieuTam(tableName)
    }
}