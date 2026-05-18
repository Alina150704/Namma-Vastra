package com.example.nammavastra.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nammavastra.MainActivity
import com.example.nammavastra.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PaymentSummaryActivity : AppCompatActivity() {

    private lateinit var tvProductName: TextView
    private lateinit var tvSellerName: TextView
    private lateinit var tvBasePrice: TextView
    private lateinit var tvShipping: TextView
    private lateinit var tvTax: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvOrderId: TextView
    private lateinit var btnProceedToReview: Button
    private lateinit var btnDownloadInvoice: Button
    private lateinit var btnBackToGallery: Button
    private lateinit var cardPaymentSummary: MaterialCardView

    private var productPrice: Double = 0.0
    private var shippingCost: Double = 0.0
    private var taxRate: Double = 0.0
    private var totalAmount: Double = 0.0
    private var productName: String = ""
    private var sellerName: String = ""
    private var orderId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_summary)

        // Get data from Chat
        productName = intent.getStringExtra("PRODUCT_NAME") ?: "Saree"
        sellerName = intent.getStringExtra("SELLER_NAME") ?: "Handloom Weaver"
        productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 4500.00)
        shippingCost = intent.getDoubleExtra("SHIPPING_COST", 100.00)
        taxRate = intent.getDoubleExtra("TAX_RATE", 0.05)

        orderId = "NV" + System.currentTimeMillis().toString().takeLast(8)

        setupViews()
        calculateAndDisplayTotal()
        setupButtons()

        // 🔥 Auto show review popup
        showReviewDialog()
    }

    private fun setupViews() {
        tvProductName = findViewById(R.id.tvProductName)
        tvSellerName = findViewById(R.id.tvSellerName)
        tvBasePrice = findViewById(R.id.tvBasePrice)
        tvShipping = findViewById(R.id.tvShipping)
        tvTax = findViewById(R.id.tvTax)
        tvTotal = findViewById(R.id.tvTotal)
        tvOrderId = findViewById(R.id.tvOrderId)
        btnProceedToReview = findViewById(R.id.btnProceedToReview)
        btnDownloadInvoice = findViewById(R.id.btnDownloadInvoice)
        btnBackToGallery = findViewById(R.id.btnBackToGallery)
        cardPaymentSummary = findViewById(R.id.cardPaymentSummary)

        tvProductName.text = productName
        tvSellerName.text = sellerName
        tvOrderId.text = orderId
    }

    private fun calculateAndDisplayTotal() {
        val taxAmount = productPrice * taxRate
        totalAmount = productPrice + shippingCost + taxAmount

        tvBasePrice.text = "₹%.2f".format(productPrice)
        tvShipping.text = "₹%.2f".format(shippingCost)
        tvTax.text = "₹%.2f (%.0f%%)".format(taxAmount, taxRate * 100)
        tvTotal.text = "₹%.2f".format(totalAmount)
    }

    private fun setupButtons() {

        btnProceedToReview.setOnClickListener {
            openReviewPage()
        }

        btnDownloadInvoice.setOnClickListener {
            Toast.makeText(this, "📄 Invoice downloaded!", Toast.LENGTH_LONG).show()
        }

        btnBackToGallery.setOnClickListener {
            goToGallery()
        }
    }

    private fun showReviewDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Leave a Review")
            .setMessage("Would you like to share your experience?")
            .setPositiveButton("Write Review") { _, _ ->
                openReviewPage()
            }
            .setNegativeButton("Later", null)
            .show()
    }

    private fun openReviewPage() {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra("PRODUCT_NAME", productName)
        intent.putExtra("SELLER_NAME", sellerName)
        intent.putExtra("AMOUNT_PAID", totalAmount)
        startActivity(intent)
    }

    private fun goToGallery() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}