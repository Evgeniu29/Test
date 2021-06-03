package com.genius.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pexelsapi.retrofit.response.PhotosItem
import com.genius.test.DashboardActivity
import com.genius.test.R

class ImageAdapter(
    var mContext: DashboardActivity,
    var mList: ArrayList<PhotosItem>,
    var clickListener: SetOnClickListener
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var searchItem1: ImageView = itemView.findViewById<ImageView>(R.id.searchItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.image_row_item,parent,false)
        return  ImageViewHolder(view)

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.searchItem1.load(mList[position].src?.small){
            placeholder(R.drawable.ic_launcher_foreground)

        }


    }

    interface SetOnClickListener{
        fun itemClicked(position: Int,photosItem:PhotosItem)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}