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
import com.example.get_your_tour_app.services.dto.TourInformationDto
import com.squareup.picasso.Picasso

class RecycleAdapter(val result: List<TourInformationDto>): RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.imageView)
        var itemName: TextView = itemView.findViewById(R.id.name)
        var itemFavoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)
        var opinions: TextView = itemView.findViewById(R.id.comments)
        var price: TextView = itemView.findViewById(R.id.price)
        var favType: Int = 0
        var rating: RatingBar = itemView.findViewById(R.id.ratingBar)
        var tourId: Int = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleAdapter.ViewHolder, position: Int) {
        val tour = result?.get(position)
        holder.itemName.text = tour.name
        holder.tourId = tour.id
        Picasso.get().load(tour.image).into(holder.itemImage)
        val text = holder.price.text
        holder.price.text = "$text${tour.price}"
        holder.rating.rating = tour.rating
        holder.opinions.text = "${tour.comments} opinions"
        if(Constants.UserId != -1) {
            holder.favType = tour.like
            changeLikeIcon(holder)
            holder.itemFavoriteButton.setOnClickListener { changeLikeIcon(holder) }
        } else {
            holder.itemFavoriteButton.visibility = View.GONE
        }
    }

    private fun changeLikeIcon(holder: RecycleAdapter.ViewHolder){
        if (holder.favType == 1) {
            holder.itemFavoriteButton.setImageResource(R.drawable.ic_action_name_fill)
            holder.favType = 0
        } else {
            holder.itemFavoriteButton.setImageResource(R.drawable.ic_action_name)
            holder.favType = 1
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }
}