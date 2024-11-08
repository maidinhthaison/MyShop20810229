package com.ql2.myshop.ui.report

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.datepicker.MaterialDatePicker
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentReportBinding
import com.ql2.myshop.ui.orders.OrdersFragment.Companion.DATETIME_DIALOG_FRAGMENT_TAG
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.DATE_ORDER_DATE
import com.ql2.myshop.utils.formatDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class ReportFragment :  BaseFragment<FragmentReportBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentReportBinding {
        return FragmentReportBinding.inflate(inflater, container, false)
    }
    private val reportViewModel by viewModels<ReportViewModel>()
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Date picker
         */
        binding.editTextDateFrom.setOnClickListener {
            showDatePicker(1)
        }
        binding.editTextDateTo.setOnClickListener {
            showDatePicker(2)

        }

        binding.buttonSearch.setOnClickListener {
            loadChart(binding.barChartRevenue, binding.barChartProfit, binding.lineChartSalesProduct)
        }

        binding.imvCancel.setOnClickListener {
            binding.editTextDateFrom.setText("")
            binding.editTextDateTo.setText("")
        }

    }
    private fun showDatePicker(what: Int) {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.title_select_date)
            .build().apply {
                addOnPositiveButtonClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
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
                }
            }.show(childFragmentManager, DATETIME_DIALOG_FRAGMENT_TAG)


    }

    private fun setUpBarChart(barChart: BarChart){
        barChart.axisRight.setDrawLabels(false)
        barChart.description.isEnabled = false
        barChart.invalidate()


        barChart.xAxis.position = XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.xAxis.isGranularityEnabled = true
        barChart.xAxis.textSize = 12f
        barChart.setPinchZoom(false)
    }
    private fun loadChart(barChartRevenue: BarChart, barChartProfit: BarChart, lineChart: LineChart){
        with(reportViewModel){
            val dateFrom = binding.editTextDateFrom.text.toString()
            val dateTo = binding.editTextDateTo.text.toString()
            if(dateFrom != "" && dateTo != ""){
                binding.tvRevenueHeader.text = String.format(getString(R.string.label_report_revenue_header_title),
                    binding.editTextDateFrom.text.toString(),binding.editTextDateTo.text.toString()
                )

                binding.tvProfitHeader.text = String.format(getString(R.string.label_report_profit_header_title),
                    binding.editTextDateFrom.text.toString(),binding.editTextDateTo.text.toString()
                )

                binding.tvProductSales.text = String.format(getString(R.string.label_report_product_sales_header_title),
                    binding.editTextDateFrom.text.toString(), binding.editTextDateTo.text.toString())

                getReportByDate(dateFrom = dateFrom.trim(), dateTo = dateTo.trim())

            }else{
                AppDialog.displayErrorMessage(
                    requireContext(), R.string.dialog_select_date_error_title,
                    R.string.dialog_select_date_error_message,
                    R.string.ok
                ) { _, _ ->

                }
            }

        }
        reportViewModel.uiGetReportByDateModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            val chartData = it.data
            if (chartData != null){
                var xValues = ArrayList<String>()
                val entries = ArrayList<BarEntry>()
                for (i in chartData.indices) {
                    xValues.add(chartData[i].formatDateTime())
                    entries.add(BarEntry(i.toFloat(), chartData[i].revenue?.toFloat()?.div(1000) ?: 0f))
                }


                val yAxis = barChartRevenue.axisLeft
                yAxis.axisMinimum = 0f
                yAxis.axisMaximum = chartData.maxOf { it1 -> it1.revenue ?: 0 }.toFloat().div(1000)
                yAxis.axisLineWidth = 2f
                yAxis.axisLineColor = Color.BLACK
                yAxis.labelCount = chartData.size
                yAxis.textColor = Color.BLACK
                yAxis.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                yAxis.textSize = 12f


                val dataSet = BarDataSet(entries, getString(R.string.label_report_revenue_title))
                dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
                dataSet.valueTextColor = Color.BLUE
                dataSet.valueTextSize = 12f
                dataSet.valueTypeface = Typeface.defaultFromStyle(Typeface.BOLD)

                val barData = BarData(dataSet)
                barChartRevenue.data = barData
                barChartRevenue.xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
                barChartRevenue.xAxis.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                barChartRevenue.xAxis.textSize = 12f
                barChartRevenue.xAxis.axisLineWidth = 2f
                barChartRevenue.xAxis.axisLineColor = Color.BLACK
                barChartRevenue.xAxis.setCenterAxisLabels(true)
                barChartRevenue.xAxis.mLabelHeight = 12
                setUpBarChart(barChartRevenue)
                /**
                 * Profit chart
                 */
                val xValuesChart = ArrayList<String>()
                val entriesChart = ArrayList<BarEntry>()
                for (i in chartData.indices) {
                    xValuesChart.add(chartData[i].formatDateTime())
                    entriesChart.add(BarEntry(i.toFloat(), chartData[i].profit?.toFloat()?.div(1000) ?: 0f))
                }


                val yAxisBarChart = barChartProfit.axisLeft
                yAxisBarChart.axisMinimum = 0f
                yAxisBarChart.axisMaximum = chartData.maxOf { it1 -> it1.profit ?: 0 }.toFloat().div(1000)
                yAxisBarChart.axisLineWidth = 2f
                yAxisBarChart.axisLineColor = Color.BLACK
                yAxisBarChart.labelCount = chartData.size
                yAxisBarChart.textColor = Color.BLACK
                yAxisBarChart.textSize = 12f
                yAxisBarChart.typeface = Typeface.defaultFromStyle(Typeface.BOLD)


                val dataSetProfit = BarDataSet(entriesChart, getString(R.string.label_report_profit_title))
                dataSetProfit.setColors(*ColorTemplate.MATERIAL_COLORS)
                dataSetProfit.valueTextColor = Color.BLUE
                dataSetProfit.valueTextSize = 12f
                dataSetProfit.valueTypeface = Typeface.defaultFromStyle(Typeface.BOLD)

                val barDataProfit = BarData(dataSetProfit)
                barChartProfit.data = barDataProfit
                barChartProfit.xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
                barChartProfit.xAxis.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                barChartProfit.xAxis.textSize = 12f
                barChartProfit.xAxis.axisLineWidth = 2f
                barChartProfit.xAxis.axisLineColor = Color.BLACK
                barChartProfit.xAxis.setCenterAxisLabels(true)

                setUpBarChart(barChartProfit)

                /**
                 * Product sales line chart
                 */

                lineChart.axisRight.setDrawLabels(false)
                val xValuesLineChart = ArrayList<String>()
                val lineEntries = ArrayList<Entry>()
                for (i in chartData.indices) {
                    xValuesLineChart.add(chartData[i].formatDateTime())
                    lineEntries.add(Entry(i.toFloat(),chartData[i].quantity?.toFloat()?: 0f))
                }


                val xAxisLineChart: XAxis = lineChart.xAxis
                xAxisLineChart.position = XAxisPosition.BOTTOM
                xAxisLineChart.valueFormatter = IndexAxisValueFormatter(xValues)
                xAxisLineChart.axisLineColor = Color.BLACK
                xAxisLineChart.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                xAxisLineChart.labelCount = 10
                xAxisLineChart.granularity = 1f
                xAxisLineChart.textSize = 12f

                val yAxisLineChart: YAxis = lineChart.axisLeft
                yAxisLineChart.axisMinimum = 0f
                yAxisLineChart.axisMaximum = chartData.maxOf { it1 -> it1.quantity ?: 0 }.toFloat()
                yAxisLineChart.axisLineWidth = 2f
                yAxisLineChart.axisLineColor = Color.RED
                yAxisLineChart.textSize = 12f
                yAxisLineChart.labelCount = chartData.size
                yAxisLineChart.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

                val lineDataSet = LineDataSet(lineEntries, getString(R.string.label_report_product_sales_title))
                lineDataSet.valueTextColor = Color.BLUE
                lineDataSet.valueTextSize = 12f
                lineDataSet.setCircleColor(Color.BLACK)
                lineDataSet.color = Color.RED
                lineDataSet.lineWidth = 2f
                lineDataSet.valueTypeface = Typeface.defaultFromStyle(Typeface.BOLD)

                val lineData = LineData(lineDataSet)

                lineChart.setData(lineData)

                lineChart.invalidate()
            }
        }


    }
}