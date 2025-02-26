package com.evening.counter.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.evening.counter.ui.components.FixedHeaderTable
import com.evening.counter.viewmodel.TableViewModel

@Composable
fun DataScreen(
    viewModel: TableViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 原有的表格组件
        FixedHeaderTable(viewModel = viewModel)
    }
}