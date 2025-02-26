package com.evening.counter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.evening.counter.ui.config.TableHeaderConfig
import com.evening.counter.ui.theme.dividerColor
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
                .shadow(elevation = 1.dp) // 添加轻微阴影
                .fillMaxWidth()
                .height(48.dp)

        ) {
            headers.forEach { header ->
                Text(
                    text = header,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
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