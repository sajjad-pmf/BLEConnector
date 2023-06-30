package ir.sajjad.bleconnector.bluetooth

import android.bluetooth.BluetoothSocket
import ir.sajjad.bleconnector.domain.TransferFailedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class BluetoothDataTransferService(
    private val socket: BluetoothSocket
) {
    fun listenForIncomingMessages(): Flow<String> {
        val delimiter = '\n'
        val bufferSize = 4000
        return flow {
            if(!socket.isConnected) {
                return@flow
            }
            var buffer = ByteArray(bufferSize)
            var bytesRead = 0
            var jsonString = ""

            while(true) {
                bytesRead = try {
                    socket.inputStream.read(buffer)
                } catch(e: IOException) {
                    throw TransferFailedException()
                }

                jsonString += buffer.decodeToString(0, bytesRead)

                val delimiterIndex = jsonString.indexOf(delimiter)
                if (delimiterIndex != -1) { // Check if the delimiter is present in the JSON string
                    val completeJsonString = jsonString.substring(0, delimiterIndex)
                    emit(completeJsonString)
                    jsonString = jsonString.substring(delimiterIndex + 1) // Remove the emitted JSON string and keep the rest
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}