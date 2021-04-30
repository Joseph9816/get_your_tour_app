package com.example.get_your_tour_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter: RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    private val names = arrayOf("Amsterdam", "Paris", "Póas Volcano")
    private val images = intArrayOf(R.drawable.amsterdam, R.drawable.eiffel_tower, R.drawable.poas_volcano)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemName: TextView

        init {
            itemImage = itemView.findViewById(R.id.imageView)
            itemName = itemView.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])
        holder.itemName.text = names[position]
    }

    override fun getItemCount(): Int {
        return names.size
    }
}