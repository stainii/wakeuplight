package stijnhooft.be.wakeuplight.backend

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.util.*


/** Singleton class keeping a bluetooth reference to the light **/
class BluetoothHelper private constructor() {

    private object HOLDER {
        val INSTANCE = BluetoothHelper()
    }

    companion object {
        val instance: BluetoothHelper by lazy { HOLDER.INSTANCE }
    }

    private val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

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
        //var bluetoothSocket = bluetoothDevice!!.createInsecureRfcommSocketToServiceRecord(uuid)
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