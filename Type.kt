package com.example.nammavastra.model

import java.util.Date

data class Saree(
    val id: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val description: String = "",
    val weaverName: String = "",
    val location: String = "",
    val timestamp: Date = Date(),
    val whatsappNumber: String = "",
    val rating: Double = 0.0  // ← ADD THIS LINE for ratings
)