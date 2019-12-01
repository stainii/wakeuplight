package stijnhooft.be.wakeuplight.backend

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket


/** Singleton class keeping a bluetooth reference to the light **/
class BluetoothManager private constructor() {

    private object HOLDER {
        val INSTANCE = BluetoothManager()
    }

    companion object {
        val INSTANCE: BluetoothManager by lazy { HOLDER.INSTANCE }
    }

    @Synchronized fun send(message: String) {
        val bluetoothSocket = connect()
        bluetoothSocket.outputStream.write(message.toByteArray())
        disconnect(bluetoothSocket)
    }

    private fun connect(): BluetoothSocket {
        enableBluetooth()
        val bluetoothDevice = findBluetoothDevice()


        val bluetoothSocket =  bluetoothDevice.javaClass
            .getMethod("createRfcommSocket", Int::class.javaPrimitiveType)
            .invoke(bluetoothDevice,1) as BluetoothSocket
        if (!bluetoothSocket.isConnected) {
            bluetoothSocket.connect()
        }
        return bluetoothSocket
    }

    private fun enableBluetooth() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.enable()
            Thread.sleep(1000L)
        }
    }

    private fun findBluetoothDevice(): BluetoothDevice {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val address = bluetoothAdapter.bondedDevices
            .stream()
            .filter { device -> device.name == "HC-05" }
            .map { device -> device.address }
            .findAny()
            .orElseThrow { IllegalStateException("No bonded bluetooth device called HC-05 found!") }

        bluetoothAdapter.cancelDiscovery()
        return bluetoothAdapter.getRemoteDevice(address)
    }

    private fun disconnect(bluetoothSocket: BluetoothSocket) {
        bluetoothSocket.inputStream.close()
        bluetoothSocket.outputStream.close()
        bluetoothSocket.close()
    }
}