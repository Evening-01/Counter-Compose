package com.evening.counter.viewmodel

// viewmodel/AccountingViewModel.kt
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evening.counter.data.entity.AccountingItem
import com.evening.counter.repository.AccountingRepository
import com.evening.counter.utils.TestDataGenerator

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AccountingViewModel @Inject constructor(
    private val repository: AccountingRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<UiModel>>(emptyList())
    val items: StateFlow<List<UiModel>> = _items

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            repository.getAllItems().collect { dbItems ->
                _items.value = dbItems.map { it.toUiModel() }
            }
        }
    }

    fun addItem(item: AccountingItem) {
        viewModelScope.launch {
            try {
                validateItem(item)
                repository.addItem(item)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "保存失败"
            }
        }
    }

    private fun validateItem(item: AccountingItem) {
        when {
            item.orderNumber.isBlank() -> throw IllegalArgumentException("单号不能为空")
            item.diameter <= 0 -> throw IllegalArgumentException("直径必须大于0")
            item.thickness <= 0 -> throw IllegalArgumentException("壁厚必须大于0")
            item.totalBundles <= 0 -> throw IllegalArgumentException("捆数必须大于0")
            item.unitPrice <= 0 -> throw IllegalArgumentException("单价必须大于0")
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    data class UiModel(
        val date: String,
        val orderNumber: String,
        val diameter: String,
        val thickness: String,
        val length: String,
        val piecesPerBundle: String,
        val bundleWeight: String,
        val totalBundles: String,
        val totalPieces: String,
        val totalWeight: String,
        val unitPrice: String,
        val totalAmount: String
    )

    private fun AccountingItem.toUiModel(): UiModel {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val piecesPerBundle = calculatePiecesPerBundle(diameter)

        return UiModel(
            date = dateFormat.format(date),
            orderNumber = orderNumber,
            diameter = "Ø${diameter}mm",
            thickness = "${thickness}mm",
            length = "${length}mm",
            piecesPerBundle = "$piecesPerBundle 支/捆",
            bundleWeight = "%.1fkg".format(bundleWeight),
            totalBundles = "${totalBundles}捆",
            totalPieces = "${totalBundles * piecesPerBundle}支",
            totalWeight = "%.1fkg".format(bundleWeight * totalBundles),
            unitPrice = "¥%.2f".format(unitPrice),
            totalAmount = "¥%.2f".format(bundleWeight * totalBundles * unitPrice)
        )
    }

    private fun calculatePiecesPerBundle(diameter: Double): Int = when {
        diameter <= 50 -> 10
        diameter <= 80 -> 5
        else -> 2
    }

    // 添加测试数据方法
    fun insertTestData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val testItems = TestDataGenerator.generateTestItems()
                testItems.forEach { repository.addItem(it) }
                Log.d("TestData", "成功插入 ${testItems.size} 条测试数据")
            } catch (e: Exception) {
                Log.e("TestData", "插入测试数据失败", e)
                _errorMessage.value = "测试数据插入失败: ${e.localizedMessage}"
            }
        }
    }



}