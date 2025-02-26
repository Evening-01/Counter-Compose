package com.evening.counter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.evening.counter.data.AppDatabase
import com.evening.counter.repository.TableItemRepository
import com.evening.counter.ui.components.MainScaffold
import com.evening.counter.ui.theme.MaterialTheme
import com.evening.counter.viewmodel.TableViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 初始化数据库
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "fixed-table-db"
        ).build()

        // 创建ViewModel
        val repository = TableItemRepository(db.tableItemDao())
        val viewModel = TableViewModel(repository)

        // 添加测试数据
        viewModel.addSampleData()

        setContent {
            MaterialTheme {
                MainScaffold(viewModel = viewModel)
            }
        }

    }
}

