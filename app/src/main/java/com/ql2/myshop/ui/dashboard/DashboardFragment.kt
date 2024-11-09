package com.ql2.myshop.ui.dashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentDashboardBinding
import com.ql2.myshop.ui.dashboard.adapter.BestSalesProductAdapter
import com.ql2.myshop.ui.dashboard.adapter.LatestOrderAdapter
import com.ql2.myshop.ui.dashboard.adapter.OutOfStockProductAdapter
import com.ql2.myshop.utils.DATE_ORDER_DATETIME
import com.ql2.myshop.utils.formatDate
import com.ql2.myshop.utils.formatPriceToCurrency
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Date


@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }
    private val dashboardViewModel by viewModels<DashboardViewModel>()

    private lateinit var outOfStockProductAdapter: OutOfStockProductAdapter

    private lateinit var bestSalesProductAdapter: BestSalesProductAdapter

    private lateinit var latestOrderAdapter: LatestOrderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindStatisticInDay()
        setupPieChart(binding.pieChartProductCategory)
        loadPieChartData(binding.pieChartProductCategory)
        loadBarChart(binding.barChart)
        initRecycleView()
        loadOutOfStockProduct(limit = 5)
    }
    private fun initRecycleView(){
        outOfStockProductAdapter =  OutOfStockProductAdapter(context = requireContext())
        binding.rvTopOutOfStock.apply {
            adapter = outOfStockProductAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        bestSalesProductAdapter =  BestSalesProductAdapter(context = requireContext())
        binding.rvTopBestSales.apply {
            adapter = bestSalesProductAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        latestOrderAdapter =  LatestOrderAdapter(context = requireContext())
        binding.rvTop3LatestOrders.apply {
            adapter = latestOrderAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun bindStatisticInDay(){
        with(dashboardViewModel) {
            getOrderInDay()
            getIncomeInDay()
            getLatestOrder()
        }
        val text = String.format(
            getString(R.string.label_total_order_today_title), formatDate(Date(), DATE_ORDER_DATETIME)
        )
        binding.tvTotalOrderInDayTitle.text = HtmlCompat.fromHtml(
            text, HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        dashboardViewModel.uiGetOrderInDayModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {

                val text = String.format(
                    getString(R.string.label_total_order_today), it.data.size.toString()
                )
                binding.tvTotalOrderInDay.text = HtmlCompat.fromHtml(
                    text, HtmlCompat.FROM_HTML_MODE_LEGACY
                )

            }
        }
        dashboardViewModel.uiGetIncomeInDayModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            val list = it.data
            if (list != null) {
                val incomeInDay = list.sumOf { it1 -> it1.totalPrice ?: 0 }
                val text = String.format(
                    getString(R.string.label_total_income_today),
                    formatPriceToCurrency(incomeInDay)
                )
                binding.tvTotalIncomeInDay.text = HtmlCompat.fromHtml(
                    text, HtmlCompat.FROM_HTML_MODE_LEGACY
                )

            }
        }
    }
    private fun loadOutOfStockProduct(limit : Int) {

        with(dashboardViewModel) {
            getOutOfStockProducts(limit)
            getBestSalesProducts(limit)

        }

        dashboardViewModel.uiOutOfStockProductModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {
                outOfStockProductAdapter.submitList(it.data.toList())
            }
        }

        dashboardViewModel.uiBestSalesProductModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {
                bestSalesProductAdapter.submitList(it.data.toList())
            }
        }

        dashboardViewModel.uiGetLatestOrderModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {
                latestOrderAdapter.submitList(it.data.toList())
            }
        }

    }
    private fun setupPieChart(pieChart: PieChart) {
        pieChart.isDrawHoleEnabled= true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText= getString(R.string.label_pieChart_product_by_cate)
        pieChart.setCenterTextSize(16f)
        pieChart.description.isEnabled= false

        val l: Legend = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    @SuppressLint("StringFormatMatches")
    private fun loadPieChartData(pieChart: PieChart) {
        with(dashboardViewModel) {
            generatePieChart()
        }

        dashboardViewModel.uiPieChartModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            val pieChartData = it.data
            if (pieChartData != null) {
                val entries = ArrayList<PieEntry>()
                val sumOfProduct = pieChartData.sumOf { it1 -> it1.numOfProduct ?: 0 }
                for (item in pieChartData) {
                    entries.add(PieEntry(item.toPercent(sumOfProduct),
                        String.format(getString(R.string.label_pieChart_numOfProduct_by_cate),
                            item.numOfProduct, item.cateName)))
                }
                val colors = ArrayList<Int>()
                for (color in ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color)
                }

                for (color in ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color)
                }

                val dataSet = PieDataSet(entries, "")
                dataSet.colors = colors

                val data = PieData(dataSet)
                data.apply {
                    setDrawValues(true)
                    setValueFormatter(PercentFormatter(pieChart))
                    setValueTextSize(12f)
                    setValueTextColor(Color.BLACK)
                }
                pieChart.apply {
                    this.data = data
                    invalidate()
                    animateY(1400, Easing.EaseInOutQuad)
                }

            }

        }
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
    private fun loadBarChart(barChart: BarChart){
        with(dashboardViewModel) {
            getIncomeInMonth()
        }
        dashboardViewModel.uiGetIncomeInMonthModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            val lineChartData = it.data
            if (lineChartData != null){
                val xValues = ArrayList<String>()
                val entries = ArrayList<BarEntry>()
                for (i in lineChartData.indices) {
                    xValues.add(lineChartData[i].formatDateTime())
                    entries.add(BarEntry(i.toFloat(), lineChartData[i].totalPrice?.toFloat()?.div(1000) ?: 0f))
                }


                val yAxis = barChart.axisLeft
                yAxis.axisMinimum = 0f
                yAxis.axisMaximum = lineChartData.maxOf { it1 -> it1.totalPrice ?: 0 }.toFloat().div(1000)
                yAxis.axisLineWidth = 2f
                yAxis.axisLineColor = Color.BLACK
                yAxis.labelCount = lineChartData.size
                yAxis.textColor = Color.BLACK
                yAxis.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                yAxis.textSize = 12f


                val dataSet = BarDataSet(entries, getString(R.string.label_dashboard_income_month_divider))
                dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
                dataSet.valueTextColor = Color.BLUE
                dataSet.valueTextSize = 12f
                dataSet.valueTypeface = Typeface.defaultFromStyle(Typeface.BOLD)

                val barData = BarData(dataSet)
                barChart.data = barData
                barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
                barChart.xAxis.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                barChart.xAxis.textSize = 12f
                barChart.xAxis.axisLineWidth = 2f
                barChart.xAxis.axisLineColor = Color.BLACK
                setUpBarChart(barChart)

            }

        }

    }

}