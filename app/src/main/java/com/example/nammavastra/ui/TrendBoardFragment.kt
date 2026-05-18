package com.example.nammavastra.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nammavastra.adapter.TrendAdapter
import com.example.nammavastra.databinding.FragmentTrendBoardBinding
import com.example.nammavastra.utils.FirestoreUtils
import com.google.android.material.snackbar.Snackbar

class TrendBoardFragment : Fragment() {

    private var _binding: FragmentTrendBoardBinding? = null
    private val binding get() = _binding!!

    private lateinit var trendAdapter: TrendAdapter
    private val firestoreUtils = FirestoreUtils()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadTrends()

        binding.swipeRefresh.setOnRefreshListener {
            loadTrends()
        }
    }

    private fun setupRecyclerView() {
        trendAdapter = TrendAdapter(emptyList())

        binding.trendRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)  // Single column
            adapter = trendAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
        }
    }

    private fun loadTrends() {
        binding.swipeRefresh.isRefreshing = true

        firestoreUtils.getTrends(
            onSuccess = { trends ->
                trendAdapter.updateTrends(trends)
                binding.swipeRefresh.isRefreshing = false

                if (trends.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.GONE
                }
            },
            onFailure = { exception ->
                binding.swipeRefresh.isRefreshing = false

                Snackbar.make(
                    binding.root,
                    "Failed to load trends: ${exception.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}