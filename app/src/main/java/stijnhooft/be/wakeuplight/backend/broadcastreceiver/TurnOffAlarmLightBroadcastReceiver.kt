package stijnhooft.be.wakeuplight.backend.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import stijnhooft.be.wakeuplight.backend.BluetoothHelper


class TurnOffAlarmLightBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val bluetoothHelper = BluetoothHelper()
            bluetoothHelper.send("0")
            bluetoothHelper.disconnect()
        } catch (e: Exception) {
            Log.e("TurnOffAlarmLightBroadcastReceiver", "Could not connect with bluetooth device", e)
        }
    }

}