package com.duong.ideatimetable.Connector

import androidx.room.*
import com.duong.ideatimetable.Entity.GhiChu

@Dao
interface GhiChuConnector {
    @Query("select * from ghichu order by id ")
    fun getAllNote(): List<GhiChu>

    @Query("select * from ghichu where id = :id")
    fun getNote(id: String): List<GhiChu>
    @Insert()
    fun taoGhiChu(ghiChu: GhiChu);
    @Delete
    fun xoaGhiChu(ghiChu: GhiChu)
    @Update
    fun suaGhiChu(ghiChu: GhiChu)
}