package com.evening.counter.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.evening.counter.ui.config.TableHeaderConfig
import com.evening.counter.viewmodel.TableViewModel

@Composable
fun FixedHeaderTable(viewModel: TableViewModel) {
    val items by viewModel.items.collectAsState(initial = emptyList())
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberLazyListState()
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current
    val headers = TableHeaderConfig.headerResIds.map { context.getString(it) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 固定表头（水平滚动同步）

        Row(
            modifier = Modifier
                .horizontalScroll(horizontalScrollState)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            headers.forEach { header ->
                Text(
                    text = header,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(8.dp),
                    color = colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        // 主体内容
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState),
            state = verticalScrollState
        ) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (items.indexOf(item) % 2 == 0) colorScheme.surface
                            else colorScheme.surfaceVariant
                        )
                ) {
                    listOf(item.column1, item.column2, item.column3).forEach { value ->
                        Text(
                            text = value,
                            modifier = Modifier
                                .width(200.dp)
                                .padding(8.dp),
                            color = colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}