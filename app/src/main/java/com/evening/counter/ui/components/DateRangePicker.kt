package com.evening.counter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    startDate: Date,
    endDate: Date,
    onDateRangeSelected: (Date, Date) -> Unit
) {
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance().apply { time = startDate }
    val startYear = calendar.get(Calendar.YEAR) - 10

    // 开始日期选择器状态
    val startPickerState = rememberDatePickerState(
        initialSelectedDateMillis = startDate.time,
        yearRange = startYear..(startYear + 20)
    )

    // 结束日期选择器状态
    val endPickerState = rememberDatePickerState(
        initialSelectedDateMillis = endDate.time,
        yearRange = startYear..(startYear + 20)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        // 日期显示区域
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateSelectionButton(
                label = "开始日期",
                date = startDate,
                onClick = { showStartPicker = true }
            )
            DateSelectionButton(
                label = "结束日期",
                date = endDate,
                onClick = { showEndPicker = true }
            )
        }

        // 开始日期选择对话框
        if (showStartPicker) {
            DatePickerDialog(
                onDismissRequest = { showStartPicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val newStart = Date(startPickerState.selectedDateMillis!!)
                            val newEnd = if (endDate.before(newStart)) newStart else endDate
                            onDateRangeSelected(newStart, newEnd)
                            showStartPicker = false
                        }
                    ) { Text("确认") }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showStartPicker = false }
                    ) { Text("取消") }
                }
            ) {
                DatePicker(state = startPickerState)
            }
        }

        // 结束日期选择对话框
        if (showEndPicker) {
            DatePickerDialog(
                onDismissRequest = { showEndPicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val newEnd = Date(endPickerState.selectedDateMillis!!)
                            val newStart = if (startDate.after(newEnd)) newEnd else startDate
                            onDateRangeSelected(newStart, newEnd)
                            showEndPicker = false
                        }
                    ) { Text("确认") }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showEndPicker = false }
                    ) { Text("取消") }
                }
            ) {
                DatePicker(state = endPickerState)
            }
        }
    }
}

@Composable
private fun DateSelectionButton(
    label: String,
    date: Date,
    onClick: () -> Unit
) {
    val dateStr = remember(date) {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }

    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp)
    ) {
        Text("$label: $dateStr")
    }
}