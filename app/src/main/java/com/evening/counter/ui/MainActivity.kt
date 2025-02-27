package com.evening.counter.ui

import MainScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.evening.counter.data.AppDatabase
import com.evening.counter.repository.TableItemRepository
import com.evening.counter.ui.components.Items
import com.evening.counter.ui.components.SettingItem
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
            MaterialTheme (dynamicColor = true) {
                MainScaffold(viewModel = viewModel)
            }
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme(dynamicColor = true) {
            Items(title = "Title", subtitle = "Subtitle", onClick = { })
        }
    }
}

