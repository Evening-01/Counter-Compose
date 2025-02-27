package com.evening.counter.repository

import com.evening.counter.data.dao.AccountingDao
import com.evening.counter.data.entity.AccountingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountingRepositoryImpl @Inject constructor(
    private val dao: AccountingDao
) : AccountingRepository {
    override fun getAllItems() = dao.getAllItems().flowOn(Dispatchers.IO)
    override suspend fun addItem(item: AccountingItem) = dao.insert(item)
}