package com.example.nammavastra.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nammavastra.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class PaymentActivity : AppCompatActivity() {

    private lateinit var tvProductName: TextView
    private lateinit var tvSellerName: TextView
    private lateinit var tvBasePrice: TextView
    private lateinit var tvShipping: TextView
    private lateinit var tvTax: TextView
    private lateinit var tvTotal: TextView
    private lateinit var etAmountPaid: TextInputEditText
    private lateinit var btnConfirmPayment: Button
    private lateinit var cardPaymentSummary: MaterialCardView

    private var productPrice: Double = 0.0
    private var shippingCost: Double = 0.0
    private var taxRate: Double = 0.0
    private var totalAmount: Double = 0.0
    private var productName: String = ""
    private var sellerName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        productName = intent.getStringExtra("PRODUCT_NAME") ?: "Saree"
        sellerName = intent.getStringExtra("SELLER_NAME") ?: "Handloom Weaver"
        productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 4500.00)
        shippingCost = intent.getDoubleExtra("SHIPPING_COST", 100.00)
        taxRate = intent.getDoubleExtra("TAX_RATE", 0.05)

        setupViews()
        calculateAndDisplayTotal()
        setupConfirmButton()
    }

    private fun setupViews() {
        tvProductName = findViewById(R.id.tvProductName)
        tvSellerName = findViewById(R.id.tvSellerName)
        tvBasePrice = findViewById(R.id.tvBasePrice)
        tvShipping = findViewById(R.id.tvShipping)
        tvTax = findViewById(R.id.tvTax)
        tvTotal = findViewById(R.id.tvTotal)
        etAmountPaid = findViewById(R.id.etAmountPaid)
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment)
        cardPaymentSummary = findViewById(R.id.cardPaymentSummary)

        tvProductName.text = productName
        tvSellerName.text = sellerName
    }

    private fun calculateAndDisplayTotal() {
        val taxAmount = productPrice * taxRate
        totalAmount = productPrice + shippingCost + taxAmount

        tvBasePrice.text = String.format("₹%.2f", productPrice)
        tvShipping.text = String.format("₹%.2f", shippingCost)
        tvTax.text = String.format("₹%.2f (%.0f%%)", taxAmount, taxRate * 100)
        tvTotal.text = String.format("₹%.2f", totalAmount)
    }

    private fun setupConfirmButton() {
        btnConfirmPayment.setOnClickListener {
            val amountPaidStr = etAmountPaid.text.toString().trim()
            if (amountPaidStr.isEmpty()) {
                etAmountPaid.error = "Please enter the amount you paid"
                return@setOnClickListener
            }

            val amountPaid = amountPaidStr.toDoubleOrNull()
            if (amountPaid == null) {
                etAmountPaid.error = "Please enter a valid amount"
                return@setOnClickListener
            }

            if (Math.abs(amountPaid - totalAmount) < 0.01) {
                // Amount matches - proceed to review page
                Toast.makeText(this, "✅ Payment confirmed! Thank you for your purchase.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ReviewActivity::class.java)
                intent.putExtra("PRODUCT_NAME", productName)
                intent.putExtra("SELLER_NAME", sellerName)
                intent.putExtra("AMOUNT_PAID", amountPaid)
                startActivity(intent)
                finish()
            } else if (amountPaid > totalAmount) {
                Toast.makeText(this, "⚠️ You have paid extra! Please contact seller for refund.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "❌ Amount mismatch! Please pay the exact amount: ₹${String.format("%.2f", totalAmount)}", Toast.LENGTH_LONG).show()
            }
        }
    }
}