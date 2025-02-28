import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evening.counter.R
import com.evening.counter.ui.screen.DataScreen
import com.evening.counter.ui.screen.SettingsScreen
import com.evening.counter.ui.theme.dividerColor
import com.evening.counter.viewmodel.AccountingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(viewModel: AccountingViewModel) {
    var selectedScreen by remember { mutableStateOf(0) }
    val screens = listOf(
        Screen.Data to Icons.Filled.List,
        Screen.Settings to Icons.Filled.Settings
    )

    MaterialTheme {
        Scaffold(
            topBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(screens[selectedScreen].first.titleRes), // 动态设置标题,
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        ),
//                        modifier = Modifier.shadow(elevation = 1.dp) // 添加轻微阴影
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
                        0 -> key("DataScreen") { DataScreen(viewModel = viewModel) }
                        1 -> key("SettingsScreen") { SettingsScreen() }
                    }
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