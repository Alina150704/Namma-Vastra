package com.example.nammavastra.model

data class Message(
    val id: String = "",
    val text: String = "",
    val isSentByUser: Boolean = false,  // true = user, false = seller
    val timestamp: Long = System.currentTimeMillis()
)