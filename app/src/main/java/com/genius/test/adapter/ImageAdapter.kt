package com.genius.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.genius.test.DashboardActivity
import com.genius.test.PaginationCallback
import com.genius.test.R
import com.genius.test.retrofit.response.PhotosItem


class ImageAdapter(
        var context: DashboardActivity,
        var list: ArrayList<PhotosItem>

): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
     var callback: PaginationCallback =   context


    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var searchItem1: ImageView = itemView.findViewById<ImageView>(R.id.searchItem1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.image_row_item, parent, false)
        return  ImageViewHolder(view)

    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.searchItem1.load(list[position].src?.small){
            placeholder(R.drawable.ic_launcher_foreground)
        }

        if(position+1!=list.size){

        }

        else {
            callback.loadNextPage();
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}