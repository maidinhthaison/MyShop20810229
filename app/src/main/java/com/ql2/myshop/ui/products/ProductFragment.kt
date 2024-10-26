package com.ql2.myshop.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentProductBinding

class ProductFragment : BaseFragment<FragmentProductBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductBinding {
        return FragmentProductBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}