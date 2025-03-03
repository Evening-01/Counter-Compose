package com.evening.counter.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.evening.counter.R
import com.evening.counter.ui.components.SectionHeader
import com.evening.counter.ui.components.SettingItem
import com.evening.counter.viewmodel.AccountingViewModel

@Composable
fun SettingsScreen(
    viewModel: AccountingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let { viewModel.exportData(context, it) }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { viewModel.importData(context, it) }
    }

    LazyColumn(modifier.fillMaxSize()) {
        item {
            SectionHeader(title = stringResource(R.string.backup_title))
            SettingItem(
                title = stringResource(R.string.export_db),
                onClick = { exportLauncher.launch("counter_backup.json") }
            )
            SettingItem(
                title = stringResource(R.string.import_db),
                onClick = { importLauncher.launch(arrayOf("application/json")) }
            )
        }
    }
}