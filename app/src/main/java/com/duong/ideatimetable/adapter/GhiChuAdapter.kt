package com.duong.ideatimetable.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.duong.ideatimetable.R
import com.duong.ideatimetable.activity.EditNoteScreen
import com.duong.ideatimetable.database.TimeTableDBHelper
import com.duong.ideatimetable.entity.GhiChu
import kotlinx.android.synthetic.main.item_container_ghichu.view.*
import java.io.ByteArrayOutputStream
import java.io.File


class GhiChuAdapter: RecyclerView.Adapter<GhiChuAdapter.GhiChuViewHolder> {
    val arr: MutableList<GhiChu>
    val context: Context

    constructor(context: Context, arr: MutableList<GhiChu>):super(){
        this.arr = arr
        this.context = context
    }
    class GhiChuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tieuDe = itemView.noiDungTieuDe
        var phuDe = itemView.noiDungPhuDe
        var anh = itemView.noiDungAnh
        var ngay = itemView.noiDungNgay
        var container = itemView.l_container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GhiChuViewHolder {
        return GhiChuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_container_ghichu, parent, false))
    }

    override fun onBindViewHolder(holder: GhiChuViewHolder, position: Int) {
        if(holder != null){
            holder.tieuDe.text = arr[position].tieuDe
            holder.phuDe.text = arr[position].phuDe
            holder.ngay.text = arr[position].ngayTao
            val b = layAnhTuPath(layAnh(arr[position].anhId))
            if(b!=null) {
                val byteArrayOutputStream = ByteArrayOutputStream()
                b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                holder.anh.setImageBitmap(b)
                holder.anh.visibility = View.VISIBLE
//                Toast.makeText(context, ""+layAnhTuUri(layAnh(arr[position].anhId))?.height, Toast.LENGTH_LONG).show()
            }
            else{
                holder.anh.visibility = View.GONE
            }
            holder.container.setOnClickListener{
                val gotoEditNote = Intent(context, EditNoteScreen::class.java)

            }
        }
    }
    override fun getItemCount(): Int {
        return arr.size
    }
    fun layAnh(id: String): String{
        if(id=="0"){ return "0"}
        val db = TimeTableDBHelper(context, "timetable.db", null, 1)
        var cursor = db.rawQuery("select * from anh where id = '"+id+"'")
        if (cursor!= null){
            cursor.moveToFirst()
            val i = cursor.getColumnIndex("url")
            if(i>-1) {
                return cursor.getString(i)
            }
        }
        return "0"
    }

    fun layAnhTuUri(strUri: String): Bitmap? {
        if (strUri == "0") return null;
        return BitmapFactory.decodeFile(strUri)
    }
    fun layAnhTuPath(strPath: String): Bitmap? {
        val imgFile = File(strPath)
        if(imgFile.exists()){
            return BitmapFactory.decodeFile(imgFile.absolutePath)
        }
        if(!strPath.contains("content")){
            return null
        }
        val strUri = Uri.parse(strPath)
        val inputSteam = strUri.let { itn -> context.contentResolver.openInputStream(itn) }
        val bitmap: Bitmap? = BitmapFactory.decodeStream(inputSteam)
        if(bitmap!= null){
            return bitmap
        }
        else
        return null
    }
}