package com.duong.ideatimetable.entity

class ThongBao(
    var id: String,
    var lapTheoNgay: Boolean,
    var ngayLap: String,
    var amBao: Boolean,
    var gioBatDau: String,
    var gioKetThuc: String,
    var lapAmBao: String
    ) {
    var _id: String = ""
    var _lapTheoNgay: Boolean = false
    var _ngayLap: String = ""
    var _amBao: Boolean = false
    var _gioBatDau: String = ""
    var _gioKetThuc: String = ""
    var _lapAmBao: String = ""

    init {
        id = _id
        lapTheoNgay = _lapTheoNgay
        ngayLap = _ngayLap
        amBao = _amBao
        gioBatDau = _gioBatDau
        gioKetThuc = _gioKetThuc
        lapAmBao = _lapAmBao
    }

}