package com.ql2.myshop.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPieChart(binding.pieChartProductCategory)
        loadPieChartData(binding.pieChartProductCategory)

    }
    private fun setupPieChart(pieChart: PieChart) {
        pieChart.isDrawHoleEnabled= true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText= getString(R.string.label_piechart_product_by_cate)
        pieChart.setCenterTextSize(24f)
        pieChart.description.isEnabled= false

        val l: Legend = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }
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
                    entries.add(PieEntry(item.toPercent(sumOfProduct), item.cateName))
                }
                val colors = ArrayList<Int>()
                for (color in ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color)
                }

                for (color in ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color)
                }

                val dataSet = PieDataSet(entries, getString(R.string.label_piechart_product_by_cate))
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

        /*val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(0.2f, "Food & Dining"))
        entries.add(PieEntry(0.15f, "Medical"))
        entries.add(PieEntry(0.10f, "Entertainment"))
        entries.add(PieEntry(0.25f, "Electricity and Gas"))
        entries.add(PieEntry(0.3f, "Housing"))*/


    }
}