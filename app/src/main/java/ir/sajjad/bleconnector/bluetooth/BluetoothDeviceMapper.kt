package ir.sajjad.bleconnector.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import ir.sajjad.bleconnector.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}