package ir.sajjad.bleconnector.viewmodels

import ir.sajjad.bleconnector.domain.Data
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf


data class BluetoothUiState(
    val scannedDevices: List<ir.sajjad.bleconnector.domain.BluetoothDevice> = emptyList(),
    val pairedDevices: List<ir.sajjad.bleconnector.domain.BluetoothDevice> = emptyList(),
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null,
    val messages: List<Data>  = emptyList(),
    val chartEntryModel: ChartEntryModel = entryModelOf(emptyList())
)
