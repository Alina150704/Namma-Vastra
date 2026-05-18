package com.example.nammavastra.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nammavastra.R
import com.example.nammavastra.databinding.FragmentWeaverStoryBinding

class WeaverStoryFragment : Fragment() {
    private var _binding: FragmentWeaverStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeaverStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load Ilkal Saree Image from local drawable folder
        binding.ivIlkalImage.setImageResource(R.drawable.ilkal_saree)

        // Load Molakalmuru Saree Image from local drawable folder
        binding.ivMolakalmuruImage.setImageResource(R.drawable.molakalmuru_saree)

        setupStoryContent()
    }

    private fun setupStoryContent() {
        // Ilkal Story with rich details
        val ilkalStory = """
            Ilkal sarees originate from the small town of Ilkal in Bagalkot district, Karnataka. 
            With a history spanning over 800 years, these sarees are known for their unique weaving 
            technique called "Tope Teni" (a special type of weaving using two shuttles).
            
            The distinctive feature of Ilkal sarees is the "Ilkal Kasuti" embroidery and the use of 
            cotton warp and art silk weft. The sarees typically feature traditional motifs like 
            "Temple", "Tower", "Lamp", "Peacock", and "Elephant".
            
            👑 Royal Patronage: These sarees were favored by the royal families of Karnataka.
            🎨 Unique Feature: The pallu (aanchal) is always in red color with white stripes.
            ⏳ Time to Weave: A single saree takes 5-7 days to complete by hand.
        """.trimIndent()

        // Molakalmuru Story
        val molakalmuruStory = """
            Molakalmuru sarees, also known as "Molakalmuru Silk Sarees," come from the Molakalmuru 
            town in Chitradurga district, Karnataka. These sarees are famous for their pure silk 
            fabric and intricate zari work.
            
            What makes Molakalmuru sarees special is the unique "Butterfly" design and the use of 
            genuine gold and silver zari. The sarees are known for their durability and the rich, 
            vibrant colors that don't fade easily.
            
            🦋 Signature Motif: The famous "Butterfly" (Chitte) design is unique to this region.
            ✨ Zari Work: Pure gold and silver zari threads are used in premium sarees.
            🌈 Color Palette: Natural dyes create vibrant, long-lasting colors.
        """.trimIndent()

        // Revival Story
        val revivalStory = """
            Today, these traditional weaving techniques are facing challenges from power looms and 
            changing fashion trends. Namma-Vastra aims to bridge this gap by connecting weavers 
            directly to urban markets, helping them understand modern color trends while preserving 
            their traditional craftsmanship.
            
            📊 Impact So Far:
            • 5,000+ weavers supported across Karnataka
            • 200+ direct market connections established
            • 40% increase in weaver income
            • 15 new design collaborations launched
            
            Each saree takes 5-7 days to complete by hand, involving over 15 different steps. 
            By supporting these weavers, we're not just buying a saree – we're preserving a legacy 
            that has been passed down through generations.
        """.trimIndent()

        binding.ilkalStoryText.text = ilkalStory
        binding.molakalmuruStoryText.text = molakalmuruStory
        binding.revivalStoryText.text = revivalStory
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}