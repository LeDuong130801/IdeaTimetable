package com.duong.ideatimetable.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.duong.ideatimetable.R
import com.duong.ideatimetable.database.TimeTableDBHelper
import com.duong.ideatimetable.entity.NhiemVu
import kotlinx.android.synthetic.main.item_container_nhiemvu.view.*

class NhiemVuAdapter : RecyclerView.Adapter<NhiemVuAdapter.NhiemVuViewHolder> {
    val arr: MutableList<NhiemVu>
    val context: Context
    constructor(context: Context, arr: MutableList<NhiemVu>){
        this.context = context
        this.arr = arr
    }
    class NhiemVuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var curBox = itemView.itemNhiemVu
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NhiemVuViewHolder {
        return NhiemVuViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_container_nhiemvu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NhiemVuViewHolder, position: Int) {
        if (holder!= null) {
            holder.curBox.text = arr[position].noiDung
            holder.curBox.isChecked = arr[position].hoanThanh
            holder.curBox.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isChecked) {
                        val db = TimeTableDBHelper(context, "timetable.db", null, 1)
                        db.execSQL("update nhiemvu set hoanthanh = 1 where id = \"" + arr[position].id + "\"")
                    } else {
                        val db = TimeTableDBHelper(context, "timetable.db", null, 1)
                        db.execSQL("update nhiemvu set hoanthanh = 0 where id = \"" + arr[position].id + "\"")
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}
