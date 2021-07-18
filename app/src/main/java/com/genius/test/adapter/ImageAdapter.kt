package com.genius.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.genius.test.Artist
import com.genius.test.R


class ImageAdapter(
    var context: Context,
    var list: ArrayList<Artist>

): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById<ImageView>(R.id.image)
        var title: TextView = itemView.findViewById<TextView>(R.id.title)
        var description: TextView = itemView.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.image_row_item, parent, false)
        return ImageViewHolder(view)

    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.image.load(list[position].photo) {
            placeholder(R.drawable.ic_launcher_foreground)
        }
        holder.title.text = list[position].title

        holder.description.text = list[position].description

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun refreshData(list: ArrayList<Artist>) {
       list.clear()
        list.addAll(list)
        notifyDataSetChanged()
    }



}
