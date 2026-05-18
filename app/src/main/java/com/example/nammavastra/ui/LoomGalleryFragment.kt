package com.example.nammavastra.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nammavastra.R
import com.example.nammavastra.adapter.SareeAdapter
import com.example.nammavastra.databinding.FragmentLoomGalleryBinding
import com.example.nammavastra.model.Saree
import com.example.nammavastra.utils.FirestoreUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.Date

class LoomGalleryFragment : Fragment() {
    private var _binding: FragmentLoomGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var sareeAdapter: SareeAdapter
    private val firestoreUtils = FirestoreUtils()
    private var selectedImageUri: android.net.Uri? = null
    private val TAG = "LoomGallery"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoomGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadSarees()

        binding.fabAddSaree.setOnClickListener {
            checkPermissionsAndPickImage()
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadSarees()
        }
    }

    private fun checkPermissionsAndPickImage() {
        Dexter.withContext(requireContext())
            .withPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        pickImage()
                    } else {
                        Snackbar.make(binding.root, "Camera and storage permissions are required to upload images", Snackbar.LENGTH_LONG).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .check()
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == android.app.Activity.RESULT_OK) {
            selectedImageUri = data?.data
            if (selectedImageUri != null) {
                showUploadDialog()
            }
        }
    }

    private fun showUploadDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_upload_saree, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.titleInput)
        val descriptionInput = dialogView.findViewById<EditText>(R.id.descriptionInput)
        val weaverNameInput = dialogView.findViewById<EditText>(R.id.weaverNameInput)
        val locationInput = dialogView.findViewById<EditText>(R.id.locationInput)
        val whatsappInput = dialogView.findViewById<EditText>(R.id.whatsappInput)

        whatsappInput.setText("918123456789")

        AlertDialog.Builder(requireContext())
            .setTitle("Upload Your Saree")
            .setView(dialogView)
            .setPositiveButton("Upload") { _, _ ->
                val title = titleInput.text.toString()
                val description = descriptionInput.text.toString()
                val weaverName = weaverNameInput.text.toString()
                val location = locationInput.text.toString()
                val whatsapp = whatsappInput.text.toString()

                if (title.isNotBlank() && description.isNotBlank() && weaverName.isNotBlank()) {
                    uploadSaree(title, description, weaverName, location, whatsapp)
                } else {
                    Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun uploadSaree(title: String, description: String, weaverName: String, location: String, whatsapp: String) {
        binding.progressBar.visibility = View.VISIBLE

        firestoreUtils.uploadSareeImage(
            uri = selectedImageUri!!,
            onSuccess = { imageUrl ->
                val saree = Saree(
                    imageUrl = imageUrl,
                    title = title,
                    description = description,
                    weaverName = weaverName,
                    location = location,
                    timestamp = Date(),
                    whatsappNumber = whatsapp,
                    rating = 0.0
                )

                firestoreUtils.saveSaree(
                    saree = saree,
                    onSuccess = {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Saree uploaded successfully!", Toast.LENGTH_LONG).show()
                        loadSarees()
                    },
                    onFailure = {
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, "Failed to save: ${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                )
            },
            onFailure = {
                binding.progressBar.visibility = View.GONE
                Snackbar.make(binding.root, "Failed to upload image: ${it.message}", Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun setupRecyclerView() {
        sareeAdapter = SareeAdapter(emptyList())
        binding.sareeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)  // Single column (1 saree per row)
            adapter = sareeAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadSarees() {
        Log.d(TAG, "Loading sarees from Firebase...")
        binding.swipeRefresh.isRefreshing = true

        firestoreUtils.getAllSarees(
            onSuccess = { sarees ->
                Log.d(TAG, "✅ Loaded ${sarees.size} sarees from Firebase")
                if (sarees.isNotEmpty()) {
                    sareeAdapter.updateSarees(sarees)
                    binding.swipeRefresh.isRefreshing = false
                    binding.emptyView.visibility = View.GONE
                } else {
                    binding.swipeRefresh.isRefreshing = false
                    loadTestSarees()
                }
            },
            onFailure = { e ->
                Log.e(TAG, "❌ Failed to load sarees: ${e.message}")
                binding.swipeRefresh.isRefreshing = false
                loadTestSarees()
            }
        )
    }

    private fun loadTestSarees() {
        val testSarees = listOf(
            Saree(
                id = "1",
                title = "Handloom Silk Saree",
                description = "Beautiful traditional handwoven silk saree with zari work. Perfect for weddings and special occasions.",
                imageUrl = "https://images.pexels.com/photos/14694561/pexels-photo-14694561.jpeg?auto=compress&cs=tinysrgb&w=800",
                weaverName = "Lakshmi Weavers Association",
                location = "Ilkal, Karnataka",
                whatsappNumber = "918123456789",
                rating = 4.8
            ),
            Saree(
                id = "2",
                title = "Cotton Kasuti Saree",
                description = "Intricate Kasuti embroidery on pure cotton. Lightweight and perfect for daily wear.",
                imageUrl = "https://images.pexels.com/photos/4493564/pexels-photo-4493564.jpeg?auto=compress&cs=tinysrgb&w=800",
                weaverName = "Shakti Handlooms",
                location = "Ilkal, Karnataka",
                whatsappNumber = "918123456789",
                rating = 4.5
            ),
            Saree(
                id = "3",
                title = "Silk Blend Wedding Saree",
                description = "Luxurious silk blend with pure gold zari. A masterpiece for your special day.",
                imageUrl = "https://images.pexels.com/photos/11755495/pexels-photo-11755495.jpeg?auto=compress&cs=tinysrgb&w=800",
                weaverName = "Molakalmuru Silk House",
                location = "Molakalmuru, Karnataka",
                whatsappNumber = "918123456789",
                rating = 5.0
            ),
            Saree(
                id = "4",
                title = "Traditional Ilkal Saree",
                description = "Authentic Ilkal with traditional temple border design. A heritage piece.",
                imageUrl = "https://images.pexels.com/photos/15824721/pexels-photo-15824721.jpeg?auto=compress&cs=tinysrgb&w=800",
                weaverName = "Ilkal Weavers Cooperative",
                location = "Ilkal, Karnataka",
                whatsappNumber = "918123456789",
                rating = 4.6
            )
        )

        sareeAdapter.updateSarees(testSarees)
        binding.swipeRefresh.isRefreshing = false
        binding.emptyView.visibility = View.GONE
        Log.d(TAG, "Loaded ${testSarees.size} test sarees")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}