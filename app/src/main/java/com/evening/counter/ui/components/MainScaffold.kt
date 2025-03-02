import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evening.counter.R
import com.evening.counter.ui.screen.DataScreen
import com.evening.counter.ui.screen.SettingsScreen
import com.evening.counter.viewmodel.AccountingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(viewModel: AccountingViewModel) {
    var selectedScreen by remember { mutableStateOf(0) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    val screens = listOf(
        Screen.Data to Icons.Filled.List,
        Screen.Settings to Icons.Filled.Settings
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    MaterialTheme {
        Scaffold(
            topBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopAppBar(
                        title = {
                            Text(
                                text = when {
                                    state.selectedIds.isNotEmpty() -> "已选择 ${state.selectedIds.size} 项"
                                    else -> stringResource(screens[selectedScreen].first.titleRes)
                                },
                                style = MaterialTheme.typography.titleLarge

                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        actions = {
                            if (selectedScreen == 0) {
                                if (state.selectedIds.isNotEmpty()) {
                                    IconButton(
                                        onClick = { showConfirmDialog = true }
                                    ) {
                                        Icon(Icons.Default.Delete, "删除")
                                    }
                                } else {
                                    IconButton(
                                        onClick = { /* 这里需要处理筛选面板显示状态 */
                                            // 需要将 showFilterSheet 状态提升到 ViewModel
                                            viewModel.toggleFilterSheet(true)
                                        }
                                    ) {
                                        Icon(Icons.Default.ArrowDropDown, "筛选")
                                    }
                                }
                            }
                        }
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    screens.forEachIndexed { index, (screen, icon) ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = stringResource(screen.titleRes),
                                    tint = if (selectedScreen == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(screen.titleRes),
                                    color = if (selectedScreen == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            selected = selectedScreen == index,
                            onClick = { selectedScreen = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Crossfade(
                    targetState = selectedScreen,
                    animationSpec = tween(durationMillis = 300),
                    label = "ScreenTransitions"
                ) { screen ->
                    when (screen) {
                        0 -> DataScreen(viewModel = viewModel)
                        1 -> SettingsScreen()
                    }
                }

                if (showConfirmDialog) {
                    AlertDialog(
                        onDismissRequest = { showConfirmDialog = false },
                        title = { Text("确认删除") },
                        text = { Text("确定要删除选中的 ${state.selectedIds.size} 项记录吗？") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.deleteSelected()
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                                    showConfirmDialog = false
                                }
                            ) {
                                Text("确认", color = MaterialTheme.colorScheme.primary)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showConfirmDialog = false }
                            ) {
                                Text("取消", color = MaterialTheme.colorScheme.onSurface)
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.surface,
                        textContentColor = MaterialTheme.colorScheme.onSurface
                    )
                }
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