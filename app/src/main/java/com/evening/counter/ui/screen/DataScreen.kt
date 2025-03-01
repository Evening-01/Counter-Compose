package com.evening.counter.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evening.counter.R
import com.evening.counter.ui.components.AccountingTable
import com.evening.counter.viewmodel.AccountingViewModel

@Composable
fun DataScreen(
    viewModel: AccountingViewModel,
    modifier: Modifier = Modifier
) {
    val items by viewModel.items.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val context = LocalContext.current // 获取 Context

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when {
            isLoading -> CircularProgressIndicator()
            items.isEmpty() -> EmptyState()
            else -> AccountingTable(items = items)
        }

        // 添加FAB
        FloatingActionButton(
            onClick = {
                Toast.makeText(context, "FAB clicked", Toast.LENGTH_SHORT).show()
                viewModel.insertTestData()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "添加新记录"
            )
        }
    }
}

@Composable
private fun EmptyState() {
    Text(
        text = stringResource(id = R.string.empty_state_message),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}