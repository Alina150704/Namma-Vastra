package com.example.nammavastra.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.nammavastra.R
import com.example.nammavastra.databinding.FragmentPriceCalculatorBinding
import java.text.NumberFormat
import java.util.*

class PriceCalculatorFragment : Fragment() {
    private var _binding: FragmentPriceCalculatorBinding? = null
    private val binding get() = _binding!!
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinners()
        setupCalculateButton()
        setupRealTimeCalculation()
        setupInfoButtons()
    }

    private fun setupSpinners() {
        // Fabric Type Spinner
        val fabricTypes = arrayOf(
            "Pure Silk (Luxury)",
            "Silk-Cotton Blend (Premium)",
            "Pure Cotton (Everyday)",
            "Art Silk (Budget Friendly)",
            "Banarasi Silk (Heritage)"
        )
        val fabricAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fabricTypes)
        fabricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fabricSpinner.adapter = fabricAdapter

        // Quality Spinner
        val qualities = arrayOf(
            "Standard (Good)",
            "Premium (Better)",
            "Luxury (Best)",
            "Masterpiece (Heritage)"
        )
        val qualityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, qualities)
        qualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.qualitySpinner.adapter = qualityAdapter

        // Labor Complexity Spinner
        val complexities = arrayOf(
            "Simple Weave (1-2 days)",
            "Medium Complexity (3-4 days)",
            "High Complexity (5-7 days)",
            "Master Artisan (7-10 days)"
        )
        val complexityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, complexities)
        complexityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.complexitySpinner.adapter = complexityAdapter
    }

    private fun setupRealTimeCalculation() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculatePrice()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.materialCostInput.addTextChangedListener(watcher)
        binding.weaverMarginInput.addTextChangedListener(watcher)
        binding.additionalCostsInput.addTextChangedListener(watcher)
        binding.designChargesInput.addTextChangedListener(watcher)
        binding.shippingCostInput.addTextChangedListener(watcher)
    }

    private fun setupInfoButtons() {
        // Weaver Margin Info Button
        binding.btnMarginInfo.setOnClickListener {
            showInfoDialog("Weaver's Margin",
                "Recommended margin based on experience:\n\n" +
                        "• Beginner (20-25%): Just starting out\n" +
                        "• Intermediate (30-35%): 1-3 years experience\n" +
                        "• Expert (40-50%): 5+ years experience\n\n" +
                        "💡 Tip: Include your skill level in pricing"
            )
        }

        // Design Info Button
        binding.btnDesignInfo.setOnClickListener {
            showInfoDialog("Design & Zari Charges",
                "• Basic design: ₹200-500\n" +
                        "• Medium complexity: ₹500-1500\n" +
                        "• High complexity (zari work): ₹1500-5000\n" +
                        "• Pure gold/silver zari: ₹5000+\n\n" +
                        "💡 Tip: Traditional motifs fetch higher prices"
            )
        }
    }

    private fun showInfoDialog(title: String, message: String) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("📊 $title")
            .setMessage(message)
            .setPositiveButton("Got it", null)
            .show()
    }

    private fun setupCalculateButton() {
        binding.calculateButton.setOnClickListener {
            calculatePriceWithAnimation()
        }

        binding.resetButton.setOnClickListener {
            resetForm()
        }

        binding.shareButton.setOnClickListener {
            sharePriceBreakdown()
        }
    }

    private fun resetForm() {
        binding.materialCostInput.text?.clear()
        binding.weaverMarginInput.setText("35")
        binding.additionalCostsInput.setText("0")
        binding.designChargesInput.setText("0")
        binding.shippingCostInput.setText("0")
        binding.fabricSpinner.setSelection(0)
        binding.qualitySpinner.setSelection(0)
        binding.complexitySpinner.setSelection(0)

        binding.resultCard.visibility = View.GONE
        Toast.makeText(requireContext(), "Form reset", Toast.LENGTH_SHORT).show()
    }

    private fun sharePriceBreakdown() {
        val finalPrice = binding.finalPriceValue.text.toString()

        val shareMessage = """
            📊 Namma-Vastra Price Quote
            
            Fabric: ${binding.fabricSpinner.selectedItem}
            Quality: ${binding.qualitySpinner.selectedItem}
            
            💰 Final Price: $finalPrice
            
            -- Calculated using Namma-Vastra Fair Price Tool
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share Price Quote"))
    }

    private fun calculatePrice() {
        val materialCostStr = binding.materialCostInput.text.toString()
        if (materialCostStr.isEmpty()) return

        val materialCost = materialCostStr.toDoubleOrNull() ?: return

        val fabricType = binding.fabricSpinner.selectedItem.toString()
        val quality = binding.qualitySpinner.selectedItem.toString()
        val complexity = binding.complexitySpinner.selectedItem.toString()
        val weaverMargin = binding.weaverMarginInput.text.toString().toDoubleOrNull() ?: 35.0
        val additionalCosts = binding.additionalCostsInput.text.toString().toDoubleOrNull() ?: 0.0
        val designCharges = binding.designChargesInput.text.toString().toDoubleOrNull() ?: 0.0
        val shippingCost = binding.shippingCostInput.text.toString().toDoubleOrNull() ?: 0.0

        // Calculate labor cost based on fabric type and complexity
        val laborCost = when {
            fabricType.contains("Pure Silk") -> 2000.0
            fabricType.contains("Banarasi") -> 3000.0
            fabricType.contains("Blend") -> 1200.0
            fabricType.contains("Cotton") -> 800.0
            else -> 500.0
        }

        val complexityMultiplier = when {
            complexity.contains("Master") -> 2.5
            complexity.contains("High") -> 2.0
            complexity.contains("Medium") -> 1.5
            else -> 1.0
        }

        val qualityMultiplier = when {
            quality.contains("Masterpiece") -> 2.5
            quality.contains("Luxury") -> 2.0
            quality.contains("Premium") -> 1.5
            else -> 1.0
        }

        val basePrice = (materialCost + laborCost + additionalCosts + designCharges + shippingCost) * qualityMultiplier * complexityMultiplier
        val weaverProfit = basePrice * (weaverMargin / 100)
        val fairRetailPrice = basePrice + weaverProfit

        val formatCurrency = { amount: Double ->
            currencyFormat.format(amount)
        }

        binding.resultCard.visibility = View.VISIBLE
        binding.basePriceValue.text = formatCurrency(basePrice)
        binding.materialCostValue.text = formatCurrency(materialCost)
        binding.laborCostValue.text = formatCurrency(laborCost * complexityMultiplier)
        binding.designChargesValue.text = formatCurrency(designCharges)
        binding.shippingValue.text = formatCurrency(shippingCost)
        binding.weaverProfitValue.text = formatCurrency(weaverProfit)
        binding.finalPriceValue.text = formatCurrency(fairRetailPrice)

        // Provide intelligent suggestion
        val suggestion = when {
            fairRetailPrice < 3000 -> "🎯 Budget-Friendly Range\nPerfect for daily wear and budget-conscious customers. Consider marketing to local boutiques and exhibitions."
            fairRetailPrice < 8000 -> "🌟 Mid-Range Premium\nIdeal for festival wear and boutique stores. Highlight the handloom quality and unique designs."
            fairRetailPrice < 15000 -> "✨ Premium Range\nPosition as luxury handloom. Focus on exclusivity, craftsmanship, and story behind the saree."
            else -> "👑 Heritage Collection\nThis is masterpiece territory. Market to high-end boutiques, wedding shoppers, and collectors. Include certificate of authenticity."
        }

        binding.priceSuggestion.text = suggestion

        // Show profit indicator
        when {
            weaverProfit < 500 -> binding.profitIndicator.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.profit_low))
            weaverProfit < 1500 -> binding.profitIndicator.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.profit_medium))
            else -> binding.profitIndicator.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.profit_high))
        }

        // Update recommendation badge
        updateRecommendationBadge(fairRetailPrice)
    }

    private fun updateRecommendationBadge(price: Double) {
        val badgeText = when {
            price < 3000 -> "💚 BEST SELLER RANGE"
            price < 8000 -> "💙 HIGHLY RECOMMENDED"
            price < 15000 -> "💜 PREMIUM PICK"
            else -> "💛 HERITAGE EDITION"
        }
        binding.recommendationBadge.text = badgeText
    }

    private fun calculatePriceWithAnimation() {
        binding.calculateButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                if (it.animatedFraction == 1f) {
                    calculatePrice()
                    binding.calculateButton.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                }
            }
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}