package ir.sajjad.bleconnector.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.sajjad.bleconnector.domain.BluetoothController
import ir.sajjad.bleconnector.domain.BluetoothDeviceDomain
import ir.sajjad.bleconnector.domain.ConnectionResult
import ir.sajjad.bleconnector.domain.Data
import com.google.gson.Gson
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entriesOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject




@HiltViewModel
class MainViewModel @Inject constructor(
    private val bluetoothController: BluetoothController
)  : ViewModel(){
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = MutableStateFlow(BluetoothUiState())
    private val _mainState = mutableStateOf(MainState())
    var mainState: State<MainState> = _mainState
    val chartEntryModelProducerFirst1 = ChartEntryModelProducer(entriesOf(0))
    val chartEntryModelProducerFirst2 = ChartEntryModelProducer(entriesOf(0))
    val composedChartEntryModelProducerFirst = chartEntryModelProducerFirst1 + chartEntryModelProducerFirst2


    val chartEntryModelProducerSecond1 = ChartEntryModelProducer(entriesOf(*mainState.value.secondChartEntryModel))
    val chartEntryModelProducerSecond2 = ChartEntryModelProducer(entriesOf(*mainState.value.secondChartEntryModel))
    val composedChartEntryModelProducerSecond = chartEntryModelProducerSecond1 + chartEntryModelProducerSecond2
    val state = combine(
        bluetoothController.scannedDevices,
        bluetoothController.pairedDevices,
        _state
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    private var deviceConnectionJob: Job? = null
    init {
//        _mainState.value = _mainState.value.copy(
//            chartEntryModel =entryModelOf(4f, 12f, 8f, 16f)
//        )
        bluetoothController.isConnected.onEach { isConnected ->
            _state.update { it.copy(isConnected = isConnected) }
        }.launchIn(viewModelScope)

        bluetoothController.errors.onEach { error ->
            _state.update { it.copy(
                errorMessage = error
            ) }
        }.launchIn(viewModelScope)
    }

    fun connectToDevice(device: BluetoothDeviceDomain) {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController
            .connectToDevice(device)
            .listen()
    }

    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        _state.update { it.copy(
            isConnecting = false,
            isConnected = false
        ) }
    }

    fun waitForIncomingConnections() {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = bluetoothController
            .startBluetoothServer()
            .listen()
    }

    fun startScan() {
        bluetoothController.startDiscovery()
    }

    fun stopScan() {
        bluetoothController.stopDiscovery()
    }

    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when(result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update { it.copy(
                        isConnected = true,
                        isConnecting = false,
                        errorMessage = null
                    ) }
                }
                is ConnectionResult.TransferSucceeded -> {
                    try {
                        val stringResult =  extractDataObjects(result.message)
                        if(_mainState.value.result.size > 12 ){
                            _mainState.value.result.removeFirst()
                            _mainState.value.result.addAll(stringResult)
                        }else{
                            _mainState.value.result.addAll(stringResult)
                        }


                        val validObjects = _mainState.value.result.map { it -> it.loadVoltage_V.toFloat()}.toTypedArray()
                        val validObjects2 = _mainState.value.result.map { it -> it.current_mA.toFloat() }.toTypedArray()
                        val validObjects3 = _mainState.value.result.map { it -> it.power_mW.toFloat()}.toTypedArray()
                        chartEntryModelProducerFirst1.setEntries(entriesOf(*validObjects))
                        chartEntryModelProducerFirst2.setEntries(entriesOf(*validObjects2))
                        chartEntryModelProducerSecond1.setEntries(entriesOf(*validObjects))
                        chartEntryModelProducerSecond2.setEntries(entriesOf(*validObjects3))

                    }catch (ex:Exception){

                        Log.d("" , "this is the ex :"+ex)
                    }

                    Log.d("debug",""+_mainState.value)
                }
                is ConnectionResult.Error -> {
                    _state.update { it.copy(
                        isConnected = false,
                        isConnecting = false,
                        errorMessage = result.message
                    ) }
                }
            }
        }
            .catch { throwable ->
                bluetoothController.closeConnection()
                _state.update { it.copy(
                    isConnected = false,
                    isConnecting = false,
                ) }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothController.release()
    }

    sealed class UIEvent {
        data class ShowToast(val message: String) : UIEvent()
    }

    fun extractDataObjects(str: String): List<Data> {
        val gson = Gson()
        val pattern = "\\{[^\\}]*\\}".toRegex()
        val matches = pattern.findAll(str)
        return matches.map { gson.fromJson(it.value, Data::class.java) }.toList()
    }
}
