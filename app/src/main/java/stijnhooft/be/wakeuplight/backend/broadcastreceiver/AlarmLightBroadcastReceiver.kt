package stijnhooft.be.wakeuplight.backend.broadcastreceiver

import android.content.Context
import android.util.Log
import stijnhooft.be.wakeuplight.backend.BluetoothHelper
import stijnhooft.be.wakeuplight.backend.domain.Alarm


class AlarmLightBroadcastReceiver : AbstractAlarmBroadcastReceiver() {

    override fun fireAlarm(alarm: Alarm, context: Context) {
        try {
            val bluetoothHelper = BluetoothHelper()
            bluetoothHelper.send("1")
            bluetoothHelper.disconnect()
        } catch (e: Exception) {
            Log.e("AlarmLightBroadcastReceiver", "Could not connect with bluetooth device", e)
        }
    }

    override fun otherTasks(alarm: Alarm, context: Context) {
        // nothing to do
    }

}