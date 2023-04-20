package com.duong.ideatimetable.database

import android.database.Cursor
import com.duong.ideatimetable.entity.*

class TimeTableGetData {
    fun getListGhiChuOf(cursor: Cursor): MutableList<GhiChu> {
        val array = mutableListOf<GhiChu>()
        if (cursor.moveToFirst()) {
            do {
                val id = getCString(cursor, "id")
                val tieuDe = getCString(cursor, "tieude")
                val phuDe = getCString(cursor, "phude")
                val noiDung = getCString(cursor, "noidung")
                val ngayTao = getCString(cursor, "ngaytap")
                val userId = getCString(cursor, "user_id")
                val thongBaoId = getCString(cursor, "thongbao_id")
                val anhId = getCString(cursor, "anh_id")
                val mauId = getCString(cursor, "mau_id")
                val trangThai = getCString(cursor, "trangthai")
                val ghiChu = GhiChu(
                    id,
                    tieuDe,
                    phuDe,
                    noiDung,
                    ngayTao,
                    userId,
                    thongBaoId,
                    anhId,
                    mauId,
                    trangThai
                )
                array.add(ghiChu)
            } while (cursor.moveToNext());
        }
        return array
    }
    fun getListNhiemVuOf(cursor: Cursor): MutableList<NhiemVu> {
        val array = mutableListOf<NhiemVu>()
        if (cursor.moveToFirst()) {
            do {
                val id = getCString(cursor, "id")
                val noiDung = getCString(cursor, "tieude")
                val hoanThanh = getCString(cursor, "phude") == "1"
                val ghiChuId = getCString(cursor, "noidung")
                val trangThai = getCString(cursor, "trangthai")
                val nhiemVu = NhiemVu(
                    id,
                    noiDung,
                    hoanThanh,
                    ghiChuId,
                    trangThai
                )
                array.add(nhiemVu)
            } while (cursor.moveToNext());
        }
        return array
    }
    fun getListMauOf(cursor: Cursor): MutableList<Mau> {
        val array = mutableListOf<Mau>()
        if (cursor.moveToFirst()) {
            do {
                val id = getCString(cursor, "id")
                val ma = getCString(cursor, "ma")
                val trangThai = getCString(cursor, "trangthai")
                val mau = Mau(
                    id,
                    ma,
                    trangThai
                )
                array.add(mau)
            } while (cursor.moveToNext());
        }
        return array
    }
    fun getListAnhOf(cursor: Cursor): MutableList<Anh> {
        val array = mutableListOf<Anh>()
        if (cursor.moveToFirst()) {
            do {
                val id = getCString(cursor, "id")
                val url = getCString(cursor, "url")
                val trangThai = getCString(cursor, "trangthai")
                val anh = Anh(
                    id,
                    url,
                    trangThai
                )
                array.add(anh)
            } while (cursor.moveToNext());
        }
        return array
    }
    fun getListThongBaoOf(cursor: Cursor): MutableList<ThongBao> {
        val array = mutableListOf<ThongBao>()
        if (cursor.moveToFirst()) {
            do {
                val id = getCString(cursor, "id")
                val lapTheoNgay = getCString(cursor, "laptheongay") == "1"
                val ngayLap = getCString(cursor, "ngaylap")
                val amBao = getCString(cursor, "ambao") == "1"
                val gioBatDau = getCString(cursor, "giobatdau")
                val gioKetThuc = getCString(cursor, "gioketthuc")
                val lapAmBao = getCString(cursor, "lapambao")
                val trangThai = getCString(cursor, "trangthai")
                val thongBao = ThongBao(
                    id,
                    lapTheoNgay,
                    ngayLap,
                    amBao,
                    gioBatDau,
                    gioKetThuc,
                    lapAmBao,
                    trangThai
                )
                array.add(thongBao)
            } while (cursor.moveToNext());
        }
        return array
    }
    private fun getCString(cursor: Cursor, name: String): String {
        val index = cursor.getColumnIndex(name)
        if (index == -1) {
            return ""
        }
        return cursor.getString(index)
    }
}