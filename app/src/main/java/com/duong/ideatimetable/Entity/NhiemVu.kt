package com.duong.ideatimetable.Entity

class NhiemVu(
    var id: String,
    var noiDung: String,
    var hoanThanh: Boolean,
    var ghiChuId: String) {
    var _id = ""
    var _noiDung = ""
    var _hoanThanh = false
    var _ghiChuId = ""
    init {
        id = _id
        noiDung = _noiDung
        hoanThanh = _hoanThanh
        ghiChuId = _ghiChuId
    }
}