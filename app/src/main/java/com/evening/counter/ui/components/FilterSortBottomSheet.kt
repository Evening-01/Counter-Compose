//package com.evening.counter.ui.components
//
//import android.widget.DatePicker
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.evening.counter.viewmodel.AccountingViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FilterSortBottomSheet(
//    viewModel: AccountingViewModel,
//    onDismiss: () -> Unit
//) {
//    val currentFilter = viewModel.filterState.value
//    var localStartDate by remember { mutableStateOf(currentFilter.startDate) }
//    var localEndDate by remember { mutableStateOf(currentFilter.endDate) }
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .navigationBarsPadding()
//        ) {
//            Text(
//                text = "选择日期范围",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            DateRangePicker(
//                startDate = localStartDate,
//                endDate = localEndDate,
//                onDateRangeSelected = { newStart, newEnd ->
//                    localStartDate = newStart
//                    localEndDate = newEnd
//                }
//            )
//
//            Button(
//                onClick = {
//                    viewModel.applyFilter(
//                        currentFilter.copy(
//                            startDate = localStartDate,
//                            endDate = localEndDate
//                        )
//                    )
//                    onDismiss()
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp)
//            ) {
//                Text("应用筛选条件")
//            }
//        }
//    }
//}