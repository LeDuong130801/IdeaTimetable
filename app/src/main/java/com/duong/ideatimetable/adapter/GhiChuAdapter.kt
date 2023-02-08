package com.duong.ideatimetable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duong.ideatimetable.R
import com.duong.ideatimetable.entity.GhiChu
import kotlinx.android.synthetic.main.item_container_ghichu.view.*

class GhiChuAdapter: RecyclerView.Adapter<GhiChuAdapter.GhiChuViewHolder> {
    val arr: Array<GhiChu>
    constructor(arr: Array<GhiChu>):super(){
        this.arr = arr
    }
    class GhiChuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tieuDe = itemView.noiDungTieuDe
        var phuDe = itemView.noiDungPhuDe
        var anh = itemView.noiDungAnh
        var ngay = itemView.noiDungNgay
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GhiChuViewHolder {
        return GhiChuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_container_ghichu, parent, false))
    }

    override fun onBindViewHolder(holder: GhiChuViewHolder, position: Int) {
        if(holder != null){
            holder.tieuDe.text = arr[position].tieuDe
            holder.phuDe.text = arr[position].phuDe
            holder.ngay.text = arr[position].ngayTao
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}