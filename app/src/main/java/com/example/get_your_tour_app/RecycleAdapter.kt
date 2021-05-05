package com.example.get_your_tour_app

import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter: RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    private val names = arrayOf("Amsterdam: The best travel of your life", "Paris: The wonders of Paris", "Póas Volcano: Mountain wonder")
    private val images = intArrayOf(R.drawable.amsterdam, R.drawable.eiffel_tower, R.drawable.poas_volcano)
    private val prices = floatArrayOf(100F, 80F, 50F)
    private val ratings = floatArrayOf(4.5F, 3F, 5F)
    private val comments = intArrayOf(10, 2, 20)
    private val likes = booleanArrayOf(true, false, false)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.imageView)
        var itemName: TextView = itemView.findViewById(R.id.name)
        var itemFavoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)
        var opinions: TextView = itemView.findViewById(R.id.comments)
        var price: TextView = itemView.findViewById(R.id.price)
        var favType: Boolean = true
        var rating: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])
        holder.itemName.text = names[position]
        var text = holder.opinions.text
        holder.opinions.text = "${comments[position]} $text"
        text = holder.price.text
        holder.price.text = "$text${prices[position]}"
        holder.rating.rating = ratings[position]
        holder.favType = likes[position]
        changeLikeIcon(holder)
        holder.itemFavoriteButton.setOnClickListener { changeLikeIcon(holder) }
    }

    private fun changeLikeIcon(holder: RecycleAdapter.ViewHolder){
        if (holder.favType) {
            holder.itemFavoriteButton.setImageResource(R.drawable.ic_action_name_fill)
            holder.favType = false
        } else {
            holder.itemFavoriteButton.setImageResource(R.drawable.ic_action_name)
            holder.favType = true
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }
}