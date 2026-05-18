package com.example.nammavastra.model

data class Review(
    val id: String = "",
    val userName: String = "",
    val productName: String = "",
    val rating: Double = 0.0,
    val reviewText: String = "",
    val date: String = ""
)