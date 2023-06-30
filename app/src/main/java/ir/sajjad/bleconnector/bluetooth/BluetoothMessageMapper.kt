package ir.sajjad.bleconnector.bluetooth

import ir.sajjad.bleconnector.domain.BluetoothMessage


fun String.toBluetoothMessage(): BluetoothMessage {
    val name = substringBeforeLast("#")
    val message = substringAfter("#")
    return BluetoothMessage(
        message = message,

    )
}

fun BluetoothMessage.toByteArray(): ByteArray {
    return "$message".encodeToByteArray()
}