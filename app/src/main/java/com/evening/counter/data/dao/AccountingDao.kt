package com.evening.counter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.evening.counter.data.entity.AccountingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountingDao {
    @Insert
    suspend fun insert(item: AccountingItem)

    @Query("SELECT * FROM accounting_items ORDER BY date DESC")
    fun getAllItems(): Flow<List<AccountingItem>>  // 确保返回Flow类型
}