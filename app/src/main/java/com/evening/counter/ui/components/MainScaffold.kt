package com.evening.counter.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.evening.counter.R
import com.evening.counter.ui.screen.DataScreen
import com.evening.counter.ui.screen.SettingsScreen
import com.evening.counter.viewmodel.TableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(viewModel: TableViewModel) {
    var selectedScreen by remember { mutableStateOf(0) }
    val items = listOf(
        Screen.Data to Icons.Filled.List,
        Screen.Settings to Icons.Filled.Settings
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                items.forEachIndexed { index, (screen, icon) ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = stringResource(screen.titleRes)
                            )
                        },
                        label = { Text(stringResource(screen.titleRes)) },
                        selected = selectedScreen == index,
                        onClick = { selectedScreen = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                0 -> DataScreen(viewModel = viewModel)
                1 -> SettingsScreen()
            }
        }
    }
}

sealed class Screen(
    @StringRes val titleRes: Int
) {
    object Data : Screen(R.string.nav_data)
    object Settings : Screen(R.string.nav_settings)
}
