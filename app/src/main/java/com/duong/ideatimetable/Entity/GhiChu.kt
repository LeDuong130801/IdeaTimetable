package com.duong.ideatimetable.Entity



class GhiChu(
    var id: String,
    var tieuDe: String,
    var phuDe: String,
    var noiDung: String,
    var ngayTao: String,
    var userId: String,

    var thongBaoId: String,
    var anhId: String,
    var mauNenId: String){

    var _id: String = ""
    var _tieuDe: String = ""
    var _phuDe: String = ""
    var _noiDung: String = ""
    var _ngayTao: String = ""
    var _userId: String = ""
    var _thongBaoId: String = ""
    var _anhId: String = ""
    var _mauNenId: String = ""
    init {
        id = _id
        tieuDe = _tieuDe
        phuDe = _phuDe
        noiDung = _noiDung
        ngayTao = _ngayTao
        userId = _userId
        thongBaoId = _thongBaoId
        anhId = _anhId
        mauNenId = _mauNenId
    }
}