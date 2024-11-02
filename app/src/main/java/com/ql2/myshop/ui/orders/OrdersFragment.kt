package com.ql2.myshop.ui.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentOrdersBinding
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import com.ql2.myshop.domain.model.orders.OrdersModel
import com.ql2.myshop.ui.orders.adapter.ListOrderAdapter
import com.ql2.myshop.ui.orders.adapter.ListOrderUIEvent
import com.ql2.myshop.utils.DATE_ORDER_DATE
import com.ql2.myshop.utils.formatDate
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Calendar

@AndroidEntryPoint
class OrdersFragment :
    BaseFragment<FragmentOrdersBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOrdersBinding {
        return FragmentOrdersBinding.inflate(inflater, container, false)
    }

    private lateinit var orderStatus: String

    private val ordersViewModel by viewModels<OrdersViewModel>()
    private lateinit var orderAdapter: ListOrderAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    Timber.d("orderStatus: $orderStatus")

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        /**
         * Date picker
         */
        binding.editTextDateFrom.setOnClickListener {
            showDatePicker(1)
        }
        binding.editTextDateTo.setOnClickListener {
           showDatePicker(2)

        }
        /**
         * Search orders
         */
        orderAdapter =  ListOrderAdapter(context = requireContext())
        binding.rvOrders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        orderAdapter.onClicked = {
            when (it) {
                is ListOrderUIEvent.OnItemClicked -> {
                    gotoDetailScreen(it.orderItemModel)
                }
            }
        }
        binding.buttonSearch.setOnClickListener {
            val dateFrom = binding.editTextDateFrom.text.toString()
            val dateTo = binding.editTextDateTo.text.toString()
            with(ordersViewModel) {
                searchOrders(orderStatus, dateFrom, dateTo)
            }

            ordersViewModel.uiSearchOrderModel.collectWhenStarted {
                binding.loadingProgress.isVisible = it.isLoading
                if (it.data != null) {
                    orderAdapter.submitList(it.data.toList())
                }

            }
        }
        /**
         * Cancel Search
         */
        binding.imvCancel.setOnClickListener {
            binding.editTextDateFrom.setText("")
            binding.editTextDateTo.setText("")
            binding.spinnerCate.setSelection(0)
        }

    }

    private fun showDatePicker(what: Int) {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.title_select_date)
            .build().apply {
                addOnPositiveButtonClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
                    /*showTimePicker(
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
                            Calendar.DAY_OF_MONTH
                        ), what
                    )*/
                    val selectDate = formatDate(
                        date = calendar,
                        outputFormat = DATE_ORDER_DATE
                    )
                    when(what){
                        1 -> {
                            binding.editTextDateFrom.setText(selectDate)
                        }
                        2 -> {
                            binding.editTextDateTo.setText(selectDate)
                        }
                    }

                    Timber.d(">>>Selected Date $selectDate")
                }
            }.show(childFragmentManager, DATETIME_DIALOG_FRAGMENT_TAG)


    }

    private fun showTimePicker(year: Int, month: Int, day: Int, what: Int) {
        val c = Calendar.getInstance()
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(c.get(Calendar.HOUR_OF_DAY))
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setMinute(c.get(Calendar.MINUTE))
            .setTitleText(R.string.title_select_time)
            .build().apply {
                addOnPositiveButtonClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day, hour, minute, 0)
                     val selectDate = formatDate(
                         date = calendar,
                         outputFormat = DATE_ORDER_DATE
                     )
                    when(what){
                        1 -> {
                            binding.editTextDateFrom.setText(selectDate)
                        }
                        2 -> {
                            binding.editTextDateTo.setText(selectDate)
                        }
                    }

                    Timber.d(">>>Selected Date $selectDate")

                }
            }.show(childFragmentManager, DATETIME_DIALOG_FRAGMENT_TAG)
    }

    private fun gotoDetailScreen(orderItemModel: OrdersModel) {
        val bundle = Bundle().apply {
            this.putSerializable(ORDER_ITEM_MODEL, orderItemModel)
        }

        findNavController().navigate(R.id.action_navigation_order_to_orderDetailFragment2, bundle)
    }
    companion object{
        const val ORDER_ITEM_MODEL = "OrderItemModel"
        const val DATETIME_DIALOG_FRAGMENT_TAG = "DateTimeDialogFragment"
    }
}