package com.example.nammavastra.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nammavastra.R
import com.example.nammavastra.databinding.ItemTrendBinding
import com.example.nammavastra.model.Trend

class TrendAdapter(private var trends: List<Trend>) :
    RecyclerView.Adapter<TrendAdapter.TrendViewHolder>() {

    class TrendViewHolder(val binding: ItemTrendBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        val binding = ItemTrendBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        val trend = trends[position]
        val context = holder.itemView.context

        holder.binding.apply {
            // Set text
            trendTitle.text = trend.title
            trendDescription.text = trend.description

            // Set month badge
            monthBadge.text = trend.month

            // Set demand badge
            val demandText = when (trend.demandLevel.lowercase()) {
                "high" -> "🔥 High Demand"
                "very high" -> "🔥🔥 Very High Demand"
                "medium" -> "📈 Medium Demand"
                else -> "📊 ${trend.demandLevel} Demand"
            }
            demandBadge.text = demandText

            // Set demand badge color
            val demandColor = when (trend.demandLevel.lowercase()) {
                "high", "very high" -> ContextCompat.getColor(root.context, R.color.profit_high)
                "medium" -> ContextCompat.getColor(root.context, R.color.profit_low)
                else -> ContextCompat.getColor(root.context, R.color.profit_medium)
            }
            demandBadge.setBackgroundColor(demandColor)

            // Clear existing chips
            colorChips.removeAllViews()

            // Add colored chips for each color with AI Toast on click
            trend.colors.forEach { colorName ->
                val chip = com.google.android.material.chip.Chip(holder.itemView.context)
                chip.text = colorName
                chip.isClickable = true
                chip.isFocusable = true

                val backgroundColor = getColorFromName(colorName)
                chip.chipBackgroundColor = android.content.res.ColorStateList.valueOf(backgroundColor)
                chip.setTextColor(getTextColorForBackground(backgroundColor))
                chip.chipStrokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#DDDDDD"))
                chip.chipStrokeWidth = 1f

                // 🎯 Show AI recommendation when color is clicked
                chip.setOnClickListener {
                    val aiMessage = getAIRecommendation(colorName)
                    Toast.makeText(context, aiMessage, Toast.LENGTH_LONG).show()
                }

                colorChips.addView(chip)
            }

            // Set color preview
            val previewColor = getColorFromName(trend.colors.firstOrNull() ?: "Pink")
            trendImage.setBackgroundColor(previewColor)

            // Add color popularity meters
            colorPopularityContainer.removeAllViews()
            trend.colors.forEach { colorName ->
                val popularity = trend.colorPopularity[colorName] ?: 50
                addPopularityBar(holder, colorName, popularity)
            }

            // Set Best For text
            bestForText.text = trend.bestFor.joinToString(", ")

            // Set Popular Cities text
            popularCitiesText.text = trend.popularCities.joinToString(", ")

            // Set Why Trending text
            whyTrendingText.text = trend.whyTrending

            // Set AI Suggestion text
            aiSuggestionText.text = trend.aiSuggestion

            // Make AI suggestion clickable
            aiSuggestionText.setOnClickListener {
                Toast.makeText(context, "🤖 ${trend.aiSuggestion}", Toast.LENGTH_LONG).show()
            }

            // Button click listeners - Fixed: using context directly
            btnViewDesigns.setOnClickListener {
                val designMessage = when {
                    trend.title.contains("Pastel") -> "🎨 Design Suggestion: Try floral motifs and light zari work for pastel sarees!"
                    trend.title.contains("Earthy") -> "🎨 Design Suggestion: Add traditional temple borders and nature-inspired patterns!"
                    trend.title.contains("Royal") -> "🎨 Design Suggestion: Use heavy zari work and intricate butta designs!"
                    trend.title.contains("Jewel") -> "🎨 Design Suggestion: Combine with gold and silver threads for a luxurious look!"
                    else -> "🎨 Design Suggestion: Explore our design gallery for inspiration!"
                }
                Toast.makeText(context, designMessage, Toast.LENGTH_LONG).show()
            }

            btnFabricMatch.setOnClickListener {
                val fabricMessage = when {
                    trend.title.contains("Pastel") -> "🧵 Fabric Match: Lightweight silk, cotton, and chiffon work best with pastels!"
                    trend.title.contains("Earthy") -> "🧵 Fabric Match: Linen, handloom cotton, and natural fabrics are ideal!"
                    trend.title.contains("Royal") -> "🧵 Fabric Match: Pure silk, banarasi silk, and velvet create a royal look!"
                    trend.title.contains("Jewel") -> "🧵 Fabric Match: Rich silks, brocade, and heavy cottons showcase jewel tones!"
                    else -> "🧵 Fabric Match: Cotton, silk, and linen are versatile choices!"
                }
                Toast.makeText(context, fabricMessage, Toast.LENGTH_LONG).show()
            }

            btnSaveTrend.setOnClickListener {
                Toast.makeText(context, "❤️ Saved ${trend.title} to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAIRecommendation(colorName: String): String {
        return when (colorName.lowercase()) {
            "mint green" -> "💚 AI Styling Tip: Mint Green looks stunning with cream or white silk. Perfect for summer day weddings! ✨"
            "lavender" -> "💜 AI Styling Tip: Lavender pairs beautifully with silver jewelry. Great for evening parties! ✨"
            "baby pink" -> "💗 AI Styling Tip: Baby Pink is trending for bridesmaids. Pair with rose gold accessories! ✨"
            "powder blue" -> "💙 AI Styling Tip: Powder Blue is perfect for daytime events. Looks great with pearl jewelry! ✨"
            "terracotta" -> "🧡 AI Styling Tip: Terracotta is earthy and elegant. Pair with oxidized silver for a bohemian look! ✨"
            "olive green" -> "💚 AI Styling Tip: Olive Green is versatile. Works well with gold jewelry and beige accessories! ✨"
            "mustard yellow" -> "💛 AI Styling Tip: Mustard Yellow is bold! Pair with maroon or navy blue for contrast! ✨"
            "rust" -> "🤎 AI Styling Tip: Rust gives a vintage feel. Pair with cream silk and antique jewelry! ✨"
            "emerald green" -> "💚 AI Styling Tip: Emerald Green is royal! Perfect for weddings with gold zari work! ✨"
            "ruby red" -> "❤️ AI Styling Tip: Ruby Red is a showstopper! Ideal for bridal collections and festive events! ✨"
            "sapphire blue" -> "💙 AI Styling Tip: Sapphire Blue looks regal. Pairs perfectly with silver and diamond jewelry! ✨"
            "amethyst" -> "💜 AI Styling Tip: Amethyst is rich and elegant. Great for evening parties and receptions! ✨"
            "coral" -> "🧡 AI Styling Tip: Coral is vibrant and fun. Perfect for beach weddings and summer parties! ✨"
            "turquoise" -> "💚 AI Styling Tip: Turquoise is refreshing! Pairs well with white and silver accessories! ✨"
            "plum" -> "💜 AI Styling Tip: Plum is sophisticated. Great for winter weddings and formal events! ✨"
            "rose gold" -> "💗 AI Styling Tip: Rose Gold is trendy! Pairs beautifully with cream, beige, and pastel colors! ✨"
            else -> "🎨 AI Styling Tip: $colorName is trending! Check our collection for matching accessories and fabrics! ✨"
        }
    }

    private fun addPopularityBar(holder: TrendViewHolder, colorName: String, popularity: Int) {
        val container = holder.binding.colorPopularityContainer

        val nameText = TextView(holder.itemView.context).apply {
            text = colorName
            textSize = 12f
            setTextColor(Color.parseColor("#333333"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val progressBar = ProgressBar(holder.itemView.context, null, android.R.attr.progressBarStyleHorizontal).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                24
            ).apply { topMargin = 4 }
            max = 100
            progress = popularity
            progressTintList = android.content.res.ColorStateList.valueOf(getPopularityColor(popularity))
        }

        val percentText = TextView(holder.itemView.context).apply {
            text = "$popularity%"
            textSize = 11f
            setTextColor(getPopularityColor(popularity))
            textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        container.addView(nameText)
        container.addView(progressBar)
        container.addView(percentText)

        val space = View(holder.itemView.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 8
            )
        }
        container.addView(space)
    }

    private fun getPopularityColor(popularity: Int): Int {
        return when {
            popularity >= 80 -> Color.parseColor("#4CAF50")
            popularity >= 60 -> Color.parseColor("#FF9800")
            popularity >= 40 -> Color.parseColor("#2196F3")
            else -> Color.parseColor("#9E9E9E")
        }
    }

    private fun getColorFromName(colorName: String): Int {
        return when (colorName.lowercase()) {
            "mint green" -> Color.parseColor("#98FB98")
            "lavender" -> Color.parseColor("#E6E6FA")
            "baby pink" -> Color.parseColor("#FFB6C1")
            "powder blue" -> Color.parseColor("#B0E0E6")
            "terracotta" -> Color.parseColor("#E2725B")
            "olive green" -> Color.parseColor("#808000")
            "mustard yellow" -> Color.parseColor("#FFDB58")
            "rust" -> Color.parseColor("#B7410E")
            "jet black" -> Color.parseColor("#343434")
            "pure white" -> Color.parseColor("#FFFFFF")
            "charcoal grey" -> Color.parseColor("#36454F")
            "indigo" -> Color.parseColor("#4B0082")
            "royal blue" -> Color.parseColor("#4169E1")
            "teal" -> Color.parseColor("#008080")
            "navy" -> Color.parseColor("#000080")
            "emerald green" -> Color.parseColor("#50C878")
            "ruby red" -> Color.parseColor("#E0115F")
            "sapphire blue" -> Color.parseColor("#0F52BA")
            "amethyst" -> Color.parseColor("#9966CC")
            "coral" -> Color.parseColor("#FF7F50")
            "turquoise" -> Color.parseColor("#40E0D0")
            "plum" -> Color.parseColor("#DDA0DD")
            "rose gold" -> Color.parseColor("#B76E79")
            else -> Color.parseColor("#FF69B4")
        }
    }

    private fun getTextColorForBackground(backgroundColor: Int): Int {
        val red = android.graphics.Color.red(backgroundColor)
        val green = android.graphics.Color.green(backgroundColor)
        val blue = android.graphics.Color.blue(backgroundColor)
        val brightness = (red * 0.299 + green * 0.587 + blue * 0.114)
        return if (brightness < 128) Color.WHITE else Color.BLACK
    }

    override fun getItemCount() = trends.size

    fun updateTrends(newTrends: List<Trend>) {
        trends = newTrends
        notifyDataSetChanged()
    }
}