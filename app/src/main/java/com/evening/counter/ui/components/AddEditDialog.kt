package com.evening.counter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.evening.counter.data.entity.AccountingItem
import com.evening.counter.viewmodel.AccountingViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDialog(
    viewModel: AccountingViewModel,
    onDismiss: () -> Unit
) {
    val errorMessage by viewModel.errorMessage.collectAsState()
    var diameter by remember { mutableStateOf("") }
    var thickness by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var orderNumber by remember { mutableStateOf("") }
    var bundleWeight by remember { mutableStateOf("") }
    var totalBundles by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // 日期显示文本
    val dateText = remember(viewModel.selectedDate.value) {
        dateFormat.format(Date(viewModel.selectedDate.value))
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加新记录") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // 日期选择行


                OutlinedButton(
                    onClick = { viewModel.showDatePicker.value = true },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = ButtonDefaults.outlinedButtonBorder,
                    // 调整内边距与其他控件对齐
                    contentPadding = PaddingValues(
                        horizontal = 16.dp, // 与 TextField 的水平对齐
                        vertical = 12.dp    // 匹配 TextField 的垂直间距
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange, // 使用更标准的日历图标
                            contentDescription = "选择日期",
                            modifier = Modifier.size(24.dp),  // 使用标准图标尺寸
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp)) // 与 TextField 的标签间距一致
                        Text(
                            text = "日期: $dateText",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 2.dp) // 微调文字垂直对齐
                        )
                    }
                }



                OutlinedTextField(
                    value = orderNumber,
                    shape = MaterialTheme.shapes.medium,
                    onValueChange = { orderNumber = it },
                    label = { Text("订单号*") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = diameter,
                        shape = MaterialTheme.shapes.medium,
                        onValueChange = { diameter = it.filter { it.isDigit() || it == '.' } },
                        label = { Text("直径(mm)*") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = thickness,
                        shape = MaterialTheme.shapes.medium,
                        onValueChange = { thickness = it.filter { it.isDigit() || it == '.' } },
                        label = { Text("壁厚(mm)*") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                OutlinedTextField(
                    value = length,
                    shape = MaterialTheme.shapes.medium,
                    onValueChange = { length = it.filter(Char::isDigit) },
                    label = { Text("长度(mm)*") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedTextField(
                    value = bundleWeight,
                    shape = MaterialTheme.shapes.medium,
                    onValueChange = { bundleWeight = it.filter { it.isDigit() || it == '.' } },
                    label = { Text("单捆重量(kg)*") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedTextField(
                    value = totalBundles,
                    shape = MaterialTheme.shapes.medium,
                    onValueChange = { totalBundles = it.filter(Char::isDigit) },
                    label = { Text("总捆数*") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedTextField(
                    value = unitPrice,
                    shape = MaterialTheme.shapes.medium,
                    onValueChange = { unitPrice = it.filter { it.isDigit() || it == '.' } },
                    label = { Text("开票价(元/kg)*") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    try {
                        val newItem = AccountingItem(
                            date = Date(),
                            orderNumber = orderNumber,
                            diameter = diameter.toDouble(),
                            thickness = thickness.toDouble(),
                            length = length.toInt(),
                            bundleWeight = bundleWeight.toDouble(),
                            totalBundles = totalBundles.toInt(),
                            unitPrice = unitPrice.toDouble()
                        )
                        viewModel.saveItem(newItem)
                    } catch (e: NumberFormatException) {
                        viewModel.setErrorMessage("请输入有效的数值")
                    }
                },
                enabled = orderNumber.isNotEmpty() &&
                        diameter.isNotEmpty() &&
                        thickness.isNotEmpty() &&
                        length.isNotEmpty() &&
                        bundleWeight.isNotEmpty() &&
                        totalBundles.isNotEmpty() &&
                        unitPrice.isNotEmpty()
            ) {
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
    if (viewModel.showDatePicker.value) {
        CustomDatePicker(
            viewModel = viewModel,
            onDismiss = { viewModel.showDatePicker.value = false }
        )
    }
}
