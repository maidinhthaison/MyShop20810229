package com.ql2.myshop.ui.dashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentDashboardBinding
import com.ql2.myshop.ui.dashboard.adapter.BestSalesProductAdapter
import com.ql2.myshop.ui.dashboard.adapter.OutOfStockProductAdapter
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPieChart(binding.pieChartProductCategory)
        loadPieChartData(binding.pieChartProductCategory)

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
}