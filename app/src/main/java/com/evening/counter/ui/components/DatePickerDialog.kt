package com.evening.counter.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.evening.counter.viewmodel.AccountingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    viewModel: AccountingViewModel,
    onDismiss: () -> Unit
) {
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = viewModel.selectedDate.value
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    dateState.selectedDateMillis?.let {
                        viewModel.onDateSelected(it)
                    }
                    onDismiss()
                }
            ) { Text("确认") }
        }
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = true
        )
    }
}
