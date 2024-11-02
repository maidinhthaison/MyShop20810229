package com.ql2.myshop.ui.orders.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseBottomSheetDialogFragment
import com.ql2.myshop.databinding.FragmentOrderDetailBinding
import com.ql2.myshop.domain.model.orders.OrdersModel
import com.ql2.myshop.ui.orders.OrdersFragment.Companion.ORDER_ITEM_MODEL
import com.ql2.myshop.ui.orders.OrdersViewModel
import com.ql2.myshop.ui.orders.adapter.OrderDetailAdapter
import com.ql2.myshop.utils.formatDateTimeServer
import com.ql2.myshop.utils.formatPriceToCurrency
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrderDetailBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentOrderDetailBinding>() {

    private val ordersViewModel by viewModels<OrdersViewModel>()
    private lateinit var orderDetailAdapter: OrderDetailAdapter
    private lateinit var orderStatus: String
    override fun initBindingObject(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentOrderDetailBinding {
        return FragmentOrderDetailBinding.inflate(inflater, container, false)
    }

    override fun isExpandDialog(): Boolean {
        return true
    }

    override fun weightOfHeight(): Float? {
        return 0.8f
    }

    @SuppressLint("SetTextI18n", "StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //expandedOffset = offsetFromTop
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        val ordersModel : OrdersModel? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(
                    ORDER_ITEM_MODEL,
                    OrdersModel::class.java
                )
            } else {
                arguments?.getSerializable(ORDER_ITEM_MODEL) as OrdersModel

            }
        /**
         * Load data
         */
        orderDetailAdapter =  OrderDetailAdapter(context = requireContext())
        binding.rvOrderDetail.apply {
            adapter = orderDetailAdapter
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        with(ordersViewModel) {
            ordersModel?.orderId?.let { getOrderDetail(it) }
        }

        ordersViewModel.uiGetOrderDetailModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {
                orderDetailAdapter.submitList(it.data.toList())
                //binding data
                if (ordersModel != null) {
                    binding.tvOrderId.text = String.format(getString(R.string.label_order_id, ordersModel.orderId))
                    binding.tvCreatedDate.text = String.format(getString(R.string.label_order_date,
                        ordersModel.createdTime?.let { it1 -> formatDateTimeServer(it1) }))
                    binding.tvFinalPrice.text = String.format(getString(R.string.label_order_final_price,
                        formatPriceToCurrency(ordersModel.finalPrice)))
                    /*binding.tvStatus.text = String.format(getString(R.string.label_order_status, ordersModel.orderStatus))
                    val id: Int = when(ordersModel.orderStatus){
                        "Paid" -> R.color.malachite
                        "Created" -> R.color.blue
                        else -> R.color.red
                    }
                    binding.tvStatus.setTextColor(context?.getColor(id) ?: R.color.black)*/
                    binding.spinnerCate.setSelection(
                        resources.getStringArray(R.array.order_status).indexOf(ordersModel.orderStatus))
                }
            }

        }
        /**
         * Order spinner
         */
        ArrayAdapter.createFromResource(requireContext(), R.array.order_status,
            android.R.layout.simple_spinner_item).also {
                adapter ->
            run {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCate.adapter = adapter
            }
        }
        binding.spinnerCate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    orderStatus = parent?.getItemAtPosition(position).toString()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        /**
         * Save
         */
        binding.buttonSave.setOnClickListener {
            Timber.d("orderStatus: $orderStatus")
        }
        /**
         * Close dialog
         */
        binding.buttonCancel.setOnClickListener {
            this.dismiss()
        }
    }

}