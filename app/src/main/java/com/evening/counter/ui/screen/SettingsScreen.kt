package com.evening.counter.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evening.counter.R
import com.evening.counter.ui.components.SectionHeader
import com.evening.counter.ui.components.SettingItem

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current // 获取 Context

    LazyColumn(modifier.fillMaxSize()) {
        // 备份与恢复模块
        item {
            SectionHeader(title = stringResource(R.string.backup_title))
            SettingItem(
                title = stringResource(R.string.export_db),
                onClick = {
                    Toast.makeText(
                        context,
                        "功能暂未实现",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            )
            SettingItem(
                title = stringResource(R.string.import_db),
                onClick = {
                    Toast.makeText(
                        context,
                        "功能暂未实现",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        // 其他设置模块...
    }
}