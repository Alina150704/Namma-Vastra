package com.example.nammavastra.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nammavastra.R
import com.example.nammavastra.adapter.ReviewAdapter
import com.example.nammavastra.model.Review
import java.text.SimpleDateFormat
import java.util.*

class ReviewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvAverageRating: TextView
    private lateinit var tvTotalReviews: TextView

    private val reviews = listOf(
        Review("1", "Anita", "Silk Saree", 5.0, "Absolutely beautiful! Loved the fabric and design.", getDate()),
        Review("2", "Meera", "Cotton Saree", 4.0, "Very comfortable and elegant for daily wear.", getDate()),
        Review("3", "Lakshmi", "Ilkal Saree", 5.0, "Traditional and classy. Worth every rupee!", getDate()),
        Review("4", "Divya", "Silk Saree", 4.5, "Nice quality but delivery was slightly delayed.", getDate()),
        Review("5", "Kavya", "Handloom Saree", 5.0, "Amazing craftsmanship. Highly recommend!", getDate()),
        Review("6", "Sneha", "Cotton Saree", 4.2, "Good product for the price.", getDate())
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerReviews)
        tvAverageRating = view.findViewById(R.id.tvAverageRating)
        tvTotalReviews = view.findViewById(R.id.tvTotalReviews)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ReviewAdapter(reviews)

        // Calculate average rating
        val avg = reviews.map { it.rating }.average()
        tvAverageRating.text = String.format("%.1f ⭐", avg)
        tvTotalReviews.text = "${reviews.size} reviews"

        return view
    }

    private fun getDate(): String {
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
    }
}