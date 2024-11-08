package com.ql2.myshop.ui.report

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.report.GetRevenueProfitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getRevenueProfitUseCase: GetRevenueProfitUseCase
) : BaseViewModel() {

    private val _uiGetReportByDateModel = MutableStateFlow(ReportByDateUIModel())
    val uiGetReportByDateModel = _uiGetReportByDateModel.asStateFlow()

    fun getReportByDate(dateFrom: String, dateTo: String) {
        viewModelScope.launch {
            getRevenueProfitUseCase(dateFrom, dateTo).collectAsState(_uiGetReportByDateModel)
        }
    }
}