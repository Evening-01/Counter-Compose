package com.evening.counter.ui.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evening.counter.R
import com.evening.counter.ui.components.AccountingTable
import com.evening.counter.ui.components.AddEditDialog
import com.evening.counter.viewmodel.AccountingViewModel


@Composable
fun DataScreen(
    viewModel: AccountingViewModel,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        when {
            uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            uiState.items.isEmpty() -> EmptyState()
            else -> AccountingTable(
                items = uiState.items,
                selectedIds = uiState.selectedIds,
                onItemClick = { item ->
                    if (uiState.selectedIds.isEmpty()) {
                        // 进入编辑逻辑
                    } else {
                        viewModel.toggleSelection(item.id)
                    }
                },
                onLongClick = { item ->
                    viewModel.toggleSelection(item.id)
                }
            )
        }

        // 添加FAB
        FloatingActionButton(
            onClick = {
                viewModel.toggleAddDialog(true)

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

        if (uiState.showAddDialog) {
            AddEditDialog(
                viewModel = viewModel,
                onDismiss = { viewModel.toggleAddDialog(false) }
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