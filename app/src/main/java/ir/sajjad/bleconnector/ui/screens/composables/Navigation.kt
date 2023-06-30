package ir.sajjad.bleconnector.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.sajjad.bleconnector.domain.BluetoothDeviceDomain
import ir.sajjad.bleconnector.viewmodels.BluetoothUiState
import ir.sajjad.bleconnector.viewmodels.MainViewModel
import kotlin.reflect.KFunction1

@Composable
fun Navigation(
    sharedViewModel: MainViewModel,
    navController: NavHostController,
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onStartServer: () -> Unit,
    onDeviceClick: KFunction1<BluetoothDeviceDomain, Unit>
) {

    NavHost(navController = navController, startDestination = "settings_screen") {
        composable("settings_screen") {
            ConfigScreen(sharedViewModel,state,onStartScan,onStopScan,onStartServer,onDeviceClick)
        }
    }
}