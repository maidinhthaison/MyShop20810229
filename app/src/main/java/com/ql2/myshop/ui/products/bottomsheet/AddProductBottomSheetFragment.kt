package com.ql2.myshop.ui.products.bottomsheet

import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseBottomSheetDialogFragment
import com.ql2.myshop.databinding.FragmentAddProductBinding
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.ProductViewModel
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.FileUtils
import com.ql2.myshop.utils.StringExt.CHAR_SPLIT
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class AddProductBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentAddProductBinding>() {

    private val productViewModel by viewModels<ProductViewModel>()

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var categoryModel: CategoryModel

    private var flag : Boolean = true

    @Inject lateinit var filesUtils: FileUtils

    override fun initBindingObject(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentAddProductBinding {
        return FragmentAddProductBinding.inflate(inflater, container, false)
    }

    override fun isExpandDialog(): Boolean {
        return true
    }

    override fun weightOfHeight(): Float? {
        return 0.8f
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //expandedOffset = offsetFromTop
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        /**
         * Spinner
         */
        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCate.adapter = arrayAdapter
        with(categoryViewModel) {
            getAllCategories()
        }
        categoryViewModel.uiGetCategoryModel.collectWhenStarted { it ->
            if (it.data != null) {
                arrayAdapter.clear()
                arrayAdapter.addAll(it.data.map { it.cateName })
                arrayAdapter.notifyDataSetChanged()
                categoryModel = it.data[0]
                binding.spinnerCate.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {

                            categoryModel = it.data[position]

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
            }
        }
        binding.buttonSave.setOnClickListener {
            val cateId = categoryModel.cateId
            val proName = binding.proNameEditText.text.toString()
            val proImportPrice = binding.proPriceEditText.text.toString()
            val proSalePrice = binding.proSalePriceEditText.text.toString()
            val proQuantity = binding.proQuantityEditText.text.toString()
            val proDes = binding.proDesEditText.text.toString()
            val proImage = binding.proImagesEditText.text.toString().trim().dropLast(2)
            with(productViewModel) {
                if (cateId != null) {
                    addNewProduct(cateId, proImportPrice.toInt(), proSalePrice.toInt(),
                        proQuantity.toInt(), proDes, proName, proImage)
                }
            }
        }
        binding.ivCloseBottomSheet.setOnClickListener { this.dismiss() }

        productViewModel.uiAddProductModel.collectWhenStarted  {
            binding.loadingProgress.isVisible = it.isLoading

            if (flag) {
                val responseModel = it.data
                if (responseModel != null){

                    AppDialog.displayErrorMessage(
                        requireContext(), R.string.dialog_add_product_title,
                        R.string.dialog_add_product_message,
                        R.string.ok
                    ) { _, _ ->
                        flag = false
                    }

                }
            }else{
                flag = true
            }

        }
        binding.buttonClear.setOnClickListener {
            binding.proNameEditText.setText("")
            binding.proPriceEditText.setText("")
            binding.proQuantityEditText.setText("")
            binding.proDesEditText.setText("")
            binding.proImagesEditText.setText("")
        }

        binding.buttonBrowseImages.setOnClickListener { imageChooser() }

    }
    private fun imageChooser() {
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)

        launchImagePicker.launch(i)
    }
    private var launchImagePicker
            : ActivityResultLauncher<Intent> = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult -> if (result.resultCode == Activity.RESULT_OK) {
            val response = result.data
            if (response != null){
                val selectedImageUri = response.data
                val fileRealPath = filesUtils.getRealPathFromURI(requireContext(), selectedImageUri!!)
                val file = File(fileRealPath)
                val fileName : String = file.name
                Timber.d("filePath : $fileName")
                val s = binding.proImagesEditText.text.toString().trim()
                if(s != ""){
                    val count = s.count { it == '@' }
                    if(count != 6){
                        val str = s.plus(fileName.plus(CHAR_SPLIT))
                        binding.proImagesEditText.setText(str)

                    }else{
                        AppDialog.displayErrorMessage(
                            requireContext(), R.string.dialog_select_image_title,
                            R.string.dialog_select_image_message,
                            R.string.ok
                        ) { _, _ ->
                            flag = false
                        }

                    }
                }else{
                    binding.proImagesEditText.setText(fileName.plus(CHAR_SPLIT))
                }

            }

        }
    }

   /* private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        when {
            // DocumentProvider
            DocumentsContract.isDocumentUri(context, uri) -> {
                when {
                    // ExternalStorageProvider
                    isExternalStorageDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":").toTypedArray()
                        val type = split[0]
                        // This is for checking Main Memory
                        return if ("primary".equals(type, ignoreCase = true)) {
                            if (split.size > 1) {
                                Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                            } else {
                                Environment.getExternalStorageDirectory().toString() + "/"
                            }
                            // This is for checking SD Card
                        } else {
                            "storage" + "/" + docId.replace(":", "/")
                        }
                    }
                    isDownloadsDocument(uri) -> {
                        val fileName = getFilePath(context, uri)
                        if (fileName != null) {
                            return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                        }
                        var id = DocumentsContract.getDocumentId(uri)
                        if (id.startsWith("raw:")) {
                            id = id.replaceFirst("raw:".toRegex(), "")
                            val file = File(id)
                            if (file.exists()) return id
                        }
                        val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                        return getDataColumn(context, contentUri, null, null)
                    }
                    isMediaDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":").toTypedArray()
                        val type = split[0]
                        var contentUri: Uri? = null
                        when (type) {
                            "image" -> {
                                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            }
                            "video" -> {
                                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            }
                            "audio" -> {
                                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            }
                        }
                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }
                }
            }
            "content".equals(uri.scheme, ignoreCase = true) -> {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            }
            "file".equals(uri.scheme, ignoreCase = true) -> {
                return uri.path
            }
        }
        return null
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                              selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            if (uri == null) return null
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    private fun getFilePath(context: Context, uri: Uri?): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        try {
            if (uri == null) return null
            cursor = context.contentResolver.query(uri, projection, null, null,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    *//**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     *//*
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    *//**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     *//*
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    *//**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     *//*
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    *//**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     *//*
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }*/
}