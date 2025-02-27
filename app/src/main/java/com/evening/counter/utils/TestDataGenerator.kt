package com.evening.counter.utils

// utils/TestDataGenerator.kt
import com.evening.counter.data.entity.AccountingItem
import java.text.SimpleDateFormat
import java.util.*

object TestDataGenerator {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun generateTestItems(): List<AccountingItem> {
        return listOf(
            createItem("2024-03-01", "ORD-001", 80.0, 2.5, 2000, 13.57, 5, 4.8),
            createItem("2024-03-05", "ORD-002", 65.0, 2.5, 2000, 10.94, 10, 4.8),
            createItem("2024-03-10", "ORD-003", 50.0, 1.8, 2000, 12.15, 8, 5.2),
            createItem("2024-03-15", "ORD-004", 100.0, 3.0, 2000, 8.15, 2, 6.0)
        )
    }

    private fun createItem(
        dateStr: String,
        orderNumber: String,
        diameter: Double,
        thickness: Double,
        length: Int,
        bundleWeight: Double,
        totalBundles: Int,
        unitPrice: Double
    ): AccountingItem {
        return AccountingItem(
            date = parseDate(dateStr),
            orderNumber = orderNumber,
            diameter = diameter,
            thickness = thickness,
            length = length,
            bundleWeight = bundleWeight,
            totalBundles = totalBundles,
            unitPrice = unitPrice
        )
    }

    private fun parseDate(dateStr: String): Date {
        return try {
            dateFormat.parse(dateStr) ?: Date()
        } catch (e: Exception) {
            Date() // 解析失败返回当前日期
        }
    }
}