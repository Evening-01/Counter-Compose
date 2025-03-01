package com.evening.counter.repository

import com.evening.counter.data.entity.AccountingItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AccountingRepository {
    fun getAllItems(): Flow<List<AccountingItem>>
    suspend fun addItem(item: AccountingItem)
    suspend fun delete(item: AccountingItem)
    suspend fun update(item: AccountingItem)
    suspend fun deleteByIds(ids: List<Long>)
    fun getItemsByDateRange(start: Date, end: Date): Flow<List<AccountingItem>>
}