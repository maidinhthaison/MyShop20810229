package com.ql2.myshop.ui.category.bottomsheet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseBottomSheetDialogFragment
import com.ql2.myshop.databinding.FragmentAddCategoryBottomSheetBinding
import com.ql2.myshop.databinding.FragmentAddProductBinding
import com.ql2.myshop.ui.category.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentAddCategoryBottomSheetBinding>() {

    private val categoryViewModel by viewModels<CategoryViewModel>()

    override fun initBindingObject(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentAddCategoryBottomSheetBinding {
        return FragmentAddCategoryBottomSheetBinding.inflate(inflater, container, false)
    }

    override fun isExpandDialog(): Boolean {
        return true
    }

    override fun weightOfHeight(): Float? {
        return 0.8f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //expandedOffset = offsetFromTop
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setupListeners() {
        binding.cateNameEditText.apply {
            addTextChangedListener(TextFieldValidation(this))
        }
        binding.cateDesEditText.apply {
            addTextChangedListener(TextFieldValidation(this))
        }

        binding.buttonSave.setOnClickListener {
            if(isValidate()){

            }
        }
        binding.buttonClear.setOnClickListener {

        }
    }
    /**
     * Validation
     */
    private fun isValidate(): Boolean =
        validateCateName() && validateCateDes()

    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.cateNameEditText -> {
                    validateCateName()
                }

                R.id.cateDesEditText -> {
                    validateCateDes()
                }

            }
        }
    }

    /**
     *field must not be empty
     */
    private fun validateCateName(): Boolean {
        return if (binding.cateNameEditText.text.toString().trim().isEmpty()) {
            binding.tilCateName.error = getString(R.string.require_field)
            binding.cateNameEditText.requestFocus()
            false
        } else {
            binding.tilCateName.isErrorEnabled = false
            true
        }
    }

    /**
     * field must not be empty
     */
    private fun validateCateDes(): Boolean {

        return if (binding.cateDesEditText.text.toString().trim().isEmpty()) {
            binding.tilCateDes.error = getString(R.string.require_field)
            binding.cateDesEditText.requestFocus()
            false
        } else {
            binding.tilCateDes.isErrorEnabled = false
            true
        }
    }

}