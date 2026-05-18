package com.example.nammavastra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nammavastra.R
import com.example.nammavastra.model.Review

class ReviewAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName: TextView = view.findViewById(R.id.tvUserName)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        val tvReviewText: TextView = view.findViewById(R.id.tvReviewText)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]

        holder.tvUserName.text = review.userName
        holder.tvProductName.text = review.productName
        holder.tvReviewText.text = review.reviewText
        holder.tvDate.text = review.date
        holder.ratingBar.rating = review.rating.toFloat()
    }

    override fun getItemCount() = reviews.size
}