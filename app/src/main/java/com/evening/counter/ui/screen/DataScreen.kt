package com.evening.counter.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.evening.counter.ui.components.AccountingTable
import com.evening.counter.viewmodel.AccountingViewModel

@Composable
fun DataScreen(
    viewModel: AccountingViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 原有的表格组件
        AccountingTable(viewModel = viewModel)
    }
}