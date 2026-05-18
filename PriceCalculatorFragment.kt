package com.example.nammavastra.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nammavastra.R
import com.example.nammavastra.databinding.ItemSareeBinding
import com.example.nammavastra.model.Saree
import com.example.nammavastra.ui.ChatActivity

class SareeAdapter(private var sarees: List<Saree>) :
    RecyclerView.Adapter<SareeAdapter.SareeViewHolder>() {

    class SareeViewHolder(val binding: ItemSareeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SareeViewHolder {
        val binding = ItemSareeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SareeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SareeViewHolder, position: Int) {
        val saree = sarees[position]
        holder.binding.apply {
            // Load image
            sareeImage.load(saree.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.placeholder_image)
                error(R.drawable.error_image)
            }

            // Set text fields
            sareeTitle.text = saree.title
            sareeDescription.text = saree.description
            weaverName.text = saree.weaverName
            location.text = saree.location

            // Show rating if available
            if (saree.rating > 0) {
                ratingBar.visibility = android.view.View.VISIBLE
                ratingBar.rating = saree.rating.toFloat()
            } else {
                ratingBar.visibility = android.view.View.GONE
            }

            // WhatsApp Button - Opens simulated chat for testing
            inquireButton.setOnClickListener {
                openChatSimulation(
                    holder.itemView.context,
                    saree.whatsappNumber,
                    saree.title,
                    saree.weaverName
                )
            }
        }
    }

    // Opens the simulated WhatsApp-like chat page (great for emulator testing)
    private fun openChatSimulation(context: Context, phoneNumber: String, productName: String, weaverName: String) {
        try {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("SELLER_NAME", weaverName)
            intent.putExtra("PRODUCT_NAME", productName)
            intent.putExtra("SELLER_PHONE", phoneNumber)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Unable to open chat: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount() = sarees.size

    fun updateSarees(newSarees: List<Saree>) {
        sarees = newSarees
        notifyDataSetChanged()
    }
}