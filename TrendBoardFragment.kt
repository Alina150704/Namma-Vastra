package com.example.nammavastra.model

data class Trend(
    val id: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val description: String = "",
    val colors: List<String> = emptyList(),
    val month: String = "",
    val colorPopularity: Map<String, Int> = emptyMap(),
    val bestFor: List<String> = emptyList(),
    val popularCities: List<String> = emptyList(),
    val demandLevel: String = "Medium",
    val whyTrending: String = "",
    val aiSuggestion: String = "",
    val season: String = "",
    // New fields for detailed color insights
    val colorMeanings: Map<String, String> = emptyMap(),  // What each color represents
    val colorCombinations: Map<String, String> = emptyMap(), // Best color combinations
    val colorOccasions: Map<String, String> = emptyMap() // Best occasions for each color
)