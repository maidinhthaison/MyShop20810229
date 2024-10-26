package com.ql2.myshop.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentProductBinding
import com.ql2.myshop.databinding.FragmentReportBinding
import com.ql2.myshop.ui.dashboard.DashboardViewModel

class ReportFragment :  BaseFragment<FragmentReportBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentReportBinding {
        return FragmentReportBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}