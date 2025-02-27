package com.evening.counter.ui

import MainScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.evening.counter.ui.components.Items
import com.evening.counter.ui.theme.MaterialTheme
import com.evening.counter.viewmodel.AccountingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme (dynamicColor = true) {
                val viewModel: AccountingViewModel = hiltViewModel()
                viewModel.insertTestData()
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

