package com.evening.counter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evening.counter.data.entity.TableItem
import com.evening.counter.repository.TableItemRepository
import kotlinx.coroutines.launch

class TableViewModel(private val repository: TableItemRepository) : ViewModel() {
    val items = repository.allItems

    fun addSampleData() {
        viewModelScope.launch {
            (1..20).forEach {
                repository.insert(
                    TableItem(
                        column1 = "Item $it-1",
                        column2 = "Item $it-2",
                        column3 = "Item $it-3"
                    )
                )
            }
        }
    }
}