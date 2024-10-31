package com.ql2.myshop.ui.bottomsheet

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.base.BaseBottomSheetDialogFragment
import com.ql2.myshop.databinding.FragmentAddProductBinding
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class AddProductBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentAddProductBinding>() {

    val productViewModel by viewModels<ProductViewModel>()

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var cateId: Int? = 0

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
                //val listBloodTypes = listCategory.map { it.cateName }
                arrayAdapter.clear()
                arrayAdapter.addAll(it.data.map { it.cateName })
                arrayAdapter.notifyDataSetChanged()

                binding.spinnerCate.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {

                            cateId = it.data[position].cateId
                            Timber.d(">>>CateId $cateId")
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
            }
        }
    }

}