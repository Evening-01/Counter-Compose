package com.evening.counter.repository

import com.evening.counter.data.entity.AccountingItem
import kotlinx.coroutines.flow.Flow

interface AccountingRepository {
    fun getAllItems(): Flow<List<AccountingItem>>
    suspend fun addItem(item: AccountingItem)
}