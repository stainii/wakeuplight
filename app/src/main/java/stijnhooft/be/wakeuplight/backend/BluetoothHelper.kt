package stijnhooft.be.wakeuplight.backend

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.util.*


class BluetoothHelper{

    private val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var bluetoothSocket = connect()

    private fun connect(): BluetoothSocket {
        enableBluetooth()
        val bluetoothDevice = findBluetoothDevice()
        val bluetoothSocket = bluetoothDevice!!.createInsecureRfcommSocketToServiceRecord(uuid)
        bluetoothSocket.connect()
        return bluetoothSocket
    }

    private fun enableBluetooth() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.enable()
            Thread.sleep(1000L)
        }
    }

    private fun disableBluetooth() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        mBluetoothAdapter.disable()
        Thread.sleep(1000L)
    }

    fun send(message: String) {
        bluetoothSocket.outputStream.write(message.toByteArray())
    }

    fun disconnect() {
        // ugly hacks to get disconnect() done properly, part 1
        Thread.sleep(1000L)

        bluetoothSocket.close()

        // ugly hacks to get disconnect() done properly, part 2
        disableBluetooth()
        enableBluetooth()
    }

    private fun findBluetoothDevice(): BluetoothDevice? {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val address = bluetoothAdapter.bondedDevices
            .stream()
            .filter { device -> device.name.equals("HC-05") }
            .map { device -> device.address }
            .findAny()
            .orElseThrow { IllegalStateException("No bonded bluetooth device called HC-05 found!") }

        return bluetoothAdapter.getRemoteDevice(address)
    }

}