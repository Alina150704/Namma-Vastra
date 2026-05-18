package com.example.nammavastra.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nammavastra.MainActivity
import com.example.nammavastra.R
import com.example.nammavastra.model.Review
import java.text.SimpleDateFormat
import java.util.*

class ReviewActivity : AppCompatActivity() {

    private lateinit var tvProductName: TextView
    private lateinit var etReviewText: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var btnSubmitReview: Button
    private lateinit var btnSkip: Button
    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var reviewsAdapter: ReviewsAdapter

    private val allReviews = mutableListOf<Review>()
    private var productName = ""
    private var sellerName = ""
    private var amountPaid = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        productName = intent.getStringExtra("PRODUCT_NAME") ?: "Saree"
        sellerName = intent.getStringExtra("SELLER_NAME") ?: "Handloom Weaver"
        amountPaid = intent.getDoubleExtra("AMOUNT_PAID", 0.0)

        setupViews()
        setupRecyclerView()
        loadSampleReviews()
        setupClickListeners()
    }

    private fun setupViews() {
        tvProductName = findViewById(R.id.tvProductName)
        etReviewText = findViewById(R.id.etReviewText)
        ratingBar = findViewById(R.id.ratingBar)
        btnSubmitReview = findViewById(R.id.btnSubmitReview)
        btnSkip = findViewById(R.id.btnSkip)
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)

        tvProductName.text = productName
    }

    private fun setupRecyclerView() {
        reviewsAdapter = ReviewsAdapter(allReviews)
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        reviewsRecyclerView.adapter = reviewsAdapter
    }

    private fun loadSampleReviews() {
        val sampleReviews = listOf(
            Review(
                id = "1",
                userName = "Priya S.",
                productName = "Handloom Silk Saree",
                rating = 5.0,
                reviewText = "Absolutely beautiful saree! The silk quality is amazing and the zari work is exquisite. Worth every penny!",
                date = "15 Jan 2025"
            ),
            Review(
                id = "2",
                userName = "Meera K.",
                productName = "Cotton Kasuti Saree",
                rating = 4.5,
                reviewText = "Very comfortable and elegant. The Kasuti embroidery is intricate and beautiful. Will buy again!",
                date = "10 Feb 2025"
            ),
            Review(
                id = "3",
                userName = "Anjali R.",
                productName = "Traditional Ilkal Saree",
                rating = 5.0,
                reviewText = "Authentic Ilkal saree with traditional border. The colors are vibrant and it's very comfortable to wear.",
                date = "5 Mar 2025"
            ),
            Review(
                id = "4",
                userName = "Sneha P.",
                productName = "Banarasi Silk Saree",
                rating = 4.8,
                reviewText = "Royal look with amazing zari work. Perfect for weddings and festivals!",
                date = "20 Mar 2025"
            ),
            Review(
                id = "5",
                userName = "Divya N.",
                productName = "Silk Blend Wedding Saree",
                rating = 5.0,
                reviewText = "Got so many compliments! The color is exactly as shown. Highly recommended!",
                date = "1 Apr 2025"
            )
        )
        allReviews.addAll(sampleReviews)
        reviewsAdapter.notifyDataSetChanged()
    }

    private fun setupClickListeners() {
        btnSubmitReview.setOnClickListener {
            val reviewText = etReviewText.text.toString().trim()
            val rating = ratingBar.rating

            if (reviewText.isEmpty()) {
                Toast.makeText(this, "Please write your review", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newReview = Review(
                id = System.currentTimeMillis().toString(),
                userName = "You",
                productName = productName,
                rating = rating.toDouble(),
                reviewText = reviewText,
                date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            )

            allReviews.add(0, newReview)
            reviewsAdapter.notifyItemInserted(0)
            reviewsRecyclerView.scrollToPosition(0)

            etReviewText.text.clear()
            ratingBar.rating = 0f

            Toast.makeText(this, "✅ Thank you for your review!", Toast.LENGTH_LONG).show()
            goToGallery()
        }

        btnSkip.setOnClickListener {
            goToGallery()
        }
    }

    private fun goToGallery() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    // Reviews Adapter
    inner class ReviewsAdapter(private val reviews: List<Review>) :
        RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_review, parent, false)
            return ReviewViewHolder(view)
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            holder.bind(reviews[position])
        }

        override fun getItemCount() = reviews.size

        inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
            private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
            private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
            private val tvReviewText: TextView = itemView.findViewById(R.id.tvReviewText)
            private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

            fun bind(review: Review) {
                tvUserName.text = review.userName
                tvProductName.text = review.productName
                ratingBar.rating = review.rating.toFloat()
                tvReviewText.text = review.reviewText
                tvDate.text = review.date
            }
        }
    }
}