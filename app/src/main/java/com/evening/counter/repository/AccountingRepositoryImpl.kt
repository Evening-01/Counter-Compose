package com.evening.counter.repository

import com.evening.counter.data.dao.AccountingDao
import com.evening.counter.data.entity.AccountingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import javax.inject.Inject

class AccountingRepositoryImpl @Inject constructor(
    private val dao: AccountingDao
) : AccountingRepository {
    // 获取所有条目
    override fun getAllItems() = dao.getAllItems().flowOn(Dispatchers.IO)

    // 添加条目
    override suspend fun addItem(item: AccountingItem) = dao.insert(item)
    // 删除单个条目
    override suspend fun delete(item: AccountingItem) = dao.delete(item)

    // 更新条目
    override suspend fun update(item: AccountingItem) = dao.update(item)

    // 批量删除条目
    override suspend fun deleteByIds(ids: List<Long>) = dao.deleteByIds(ids)

    // 按日期范围查询
    override fun getItemsByDateRange(start: Date, end: Date): Flow<List<AccountingItem>> {
        return dao.getItemsByDateRange(start, end).flowOn(Dispatchers.IO)
    }
}