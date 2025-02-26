package com.evening.counter.data.dao

import androidx.room.*
import com.evening.counter.data.entity.TableItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TableItemDao {
    @Query("SELECT * FROM items")
    fun getAll(): Flow<List<TableItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TableItem)
}