package com.example.nammavastra.utils

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.example.nammavastra.model.Saree
import com.example.nammavastra.model.Trend
import java.util.Date
import java.util.UUID

class FirestoreUtils {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val TAG = "FirestoreUtils"

    fun uploadSareeImage(uri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val fileName = "sarees/${UUID.randomUUID()}.jpg"
        val storageRef = storage.reference.child(fileName)

        Log.d(TAG, "Uploading image to: $fileName")

        storageRef.putFile(uri)
            .addOnSuccessListener {
                Log.d(TAG, "Upload successful, getting download URL")
                storageRef.downloadUrl.addOnSuccessListener { url ->
                    Log.d(TAG, "Download URL: $url")
                    onSuccess(url.toString())
                }.addOnFailureListener {
                    Log.e(TAG, "Failed to get download URL", it)
                    onFailure(it)
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "Failed to upload image", it)
                onFailure(it)
            }
    }

    fun saveSaree(saree: Saree, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        Log.d(TAG, "Saving saree: ${saree.title}")

        db.collection("sarees")
            .add(saree)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Saree saved with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error saving saree", e)
                onFailure(e)
            }
    }

    fun getAllSarees(onSuccess: (List<Saree>) -> Unit, onFailure: (Exception) -> Unit) {
        Log.d(TAG, "🔄 Loading all sarees from Firestore...")

        db.collection("sarees")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "✅ Success! Got ${result.documents.size} documents from Firestore")

                val sarees = result.documents.mapNotNull { doc ->
                    try {
                        val saree = doc.toObject(Saree::class.java)
                        if (saree != null) {
                            Log.d(TAG, "📦 Loaded: ${saree.title} (Rating: ${saree.rating})")
                            saree.copy(id = doc.id)
                        } else {
                            Log.e(TAG, "❌ Failed to convert document: ${doc.id}")
                            null
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Error converting document ${doc.id}: ${e.message}")
                        null
                    }
                }

                Log.d(TAG, "✅ Successfully loaded ${sarees.size} sarees from Firestore")
                onSuccess(sarees)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ FIRESTORE ERROR: ${e.message}", e)
                onFailure(e)
            }
    }

    fun getTrends(onSuccess: (List<Trend>) -> Unit, onFailure: (Exception) -> Unit) {
        Log.d(TAG, "🔄 Loading trends from Firestore...")

        db.collection("trends")
            .get()
            .addOnSuccessListener { result ->
                val trends = result.documents.mapNotNull { doc ->
                    doc.toObject(Trend::class.java)?.copy(id = doc.id)
                }

                if (trends.isNotEmpty()) {
                    Log.d(TAG, "✅ Loaded ${trends.size} trends from Firestore")
                    onSuccess(trends)
                } else {
                    Log.d(TAG, "⚠️ No trends found in Firestore, using enhanced sample data")
                    onSuccess(getEnhancedSampleTrends())
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Error loading trends from Firestore", e)
                onSuccess(getEnhancedSampleTrends())
            }
    }

    private fun getEnhancedSampleTrends(): List<Trend> {
        return listOf(
            Trend(
                id = "1",
                imageUrl = "",
                title = "🌸 Pastel Paradise",
                description = "Soft pastel shades taking the urban fashion by storm",
                colors = listOf("Mint Green", "Lavender", "Baby Pink", "Powder Blue"),
                month = "December 2024",
                colorPopularity = mapOf(
                    "Mint Green" to 80,
                    "Lavender" to 65,
                    "Baby Pink" to 75,
                    "Powder Blue" to 85
                ),
                bestFor = listOf("Cotton Kurtis", "Summer Sarees", "Bridesmaid Dresses"),
                popularCities = listOf("Bengaluru", "Chennai", "Mumbai"),
                demandLevel = "High",
                whyTrending = "Summer collection releases and wedding season demand ☀️",
                aiSuggestion = "Pair Mint Green with cream silk for a fresh summer look. Lavender complements gold jewelry beautifully.",
                season = "Summer"
            ),
            Trend(
                id = "2",
                imageUrl = "",
                title = "🌿 Earthy Elegance",
                description = "Natural dyes and earthy tones for sustainable fashion",
                colors = listOf("Terracotta", "Olive Green", "Mustard Yellow", "Rust"),
                month = "December 2024",
                colorPopularity = mapOf(
                    "Terracotta" to 90,
                    "Olive Green" to 70,
                    "Mustard Yellow" to 60,
                    "Rust" to 75
                ),
                bestFor = listOf("Linen Sarees", "Handloom Shirts", "Festival Wear"),
                popularCities = listOf("Mysuru", "Kochi", "Pune"),
                demandLevel = "High",
                whyTrending = "Eco-fashion movement and sustainable clothing trend 🌱",
                aiSuggestion = "Terracotta pairs beautifully with off-white linen. Add oxidized silver jewelry for a bohemian look.",
                season = "All-season"
            ),
            Trend(
                id = "3",
                imageUrl = "",
                title = "⚫ Monochrome Magic",
                description = "Classic black and white with modern geometric patterns",
                colors = listOf("Jet Black", "Pure White", "Charcoal Grey"),
                month = "December 2024",
                colorPopularity = mapOf(
                    "Jet Black" to 95,
                    "Pure White" to 85,
                    "Charcoal Grey" to 70
                ),
                bestFor = listOf("Office Wear", "Cocktail Dresses", "Party Wear"),
                popularCities = listOf("Delhi", "Mumbai", "Hyderabad"),
                demandLevel = "High",
                whyTrending = "Minimalist fashion trend and corporate wear demand 🖤",
                aiSuggestion = "Create contrast by pairing black with white accessories. Grey works well with pastel blouses.",
                season = "All-season"
            ),
            Trend(
                id = "4",
                imageUrl = "",
                title = "💙 Royal Blues",
                description = "Deep indigos and royal blues for festive occasions",
                colors = listOf("Indigo", "Royal Blue", "Teal", "Navy"),
                month = "November 2024",
                colorPopularity = mapOf(
                    "Indigo" to 85,
                    "Royal Blue" to 90,
                    "Teal" to 75,
                    "Navy" to 80
                ),
                bestFor = listOf("Wedding Sarees", "Evening Gowns", "Festival Wear"),
                popularCities = listOf("Jaipur", "Lucknow", "Kolkata"),
                demandLevel = "Very High",
                whyTrending = "Wedding season peak demand and festive celebrations 💙",
                aiSuggestion = "Royal Blue with silver zari creates a stunning evening look. Indigo pairs well with mustard yellow accents.",
                season = "Winter"
            ),
            Trend(
                id = "5",
                imageUrl = "",
                title = "💎 Jewel Tones",
                description = "Rich gemstone-inspired colors for luxury handloom collection",
                colors = listOf("Emerald Green", "Ruby Red", "Sapphire Blue", "Amethyst"),
                month = "October 2024",
                colorPopularity = mapOf(
                    "Emerald Green" to 88,
                    "Ruby Red" to 92,
                    "Sapphire Blue" to 85,
                    "Amethyst" to 78
                ),
                bestFor = listOf("Luxury Sarees", "Bridal Lehengas", "Gala Dinners"),
                popularCities = listOf("Bengaluru", "Mumbai", "Delhi"),
                demandLevel = "High",
                whyTrending = "Growing demand for gemstone-inspired wedding collections 💎",
                aiSuggestion = "Emerald green with gold zari is perfect for weddings. Ruby red looks stunning with pearl jewelry.",
                season = "Wedding Season"
            ),
            Trend(
                id = "6",
                imageUrl = "",
                title = "🌅 Sunset Dreams",
                description = "Warm orange, coral, and pink hues inspired by evening skies",
                colors = listOf("Coral", "Turquoise", "Plum", "Rose Gold"),
                month = "October 2024",
                colorPopularity = mapOf(
                    "Coral" to 82,
                    "Turquoise" to 70,
                    "Plum" to 75,
                    "Rose Gold" to 88
                ),
                bestFor = listOf("Beach Wear", "Destination Weddings", "Summer Parties"),
                popularCities = listOf("Goa", "Chennai", "Kochi"),
                demandLevel = "Medium",
                whyTrending = "Beach wedding season and summer party trends 🌅",
                aiSuggestion = "Coral and turquoise create a vibrant beach look. Rose gold adds a touch of glamour to any outfit.",
                season = "Summer"
            )
        )
    }
}