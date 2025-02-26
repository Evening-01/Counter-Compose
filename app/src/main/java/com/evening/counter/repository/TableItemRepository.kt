package com.evening.counter.repository

import com.evening.counter.data.dao.TableItemDao
import com.evening.counter.data.entity.TableItem
import kotlinx.coroutines.flow.Flow

class TableItemRepository(private val dao: TableItemDao) {
    val allItems: Flow<List<TableItem>> = dao.getAll()

    suspend fun insert(item: TableItem) {
        dao.insert(item)
    }
}