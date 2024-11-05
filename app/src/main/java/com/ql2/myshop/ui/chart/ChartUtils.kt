package com.ql2.myshop.ui.chart

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.DecimalFormat


object ChartUtils {
    internal class Data(val xValue: Float, val yValue: Float, val xAxisValue: String)

    internal class ValueFormatter : IValueFormatter {
        private val mFormat: DecimalFormat = DecimalFormat("######.0")


        override fun getFormattedValue(
            value: Float,
            entry: Entry?,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler?
        ): String {
            return  mFormat.format(value)
        }
    }
}