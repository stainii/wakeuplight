package stijnhooft.be.wakeuplight.backend.broadcastreceiver

import android.content.Context
import android.util.Log
import stijnhooft.be.wakeuplight.backend.LightManager
import stijnhooft.be.wakeuplight.backend.domain.Alarm


class AlarmLightBroadcastReceiver : AbstractAlarmBroadcastReceiver() {

    override fun fireAlarm(alarm: Alarm, context: Context) {
        try {
            LightManager.INSTANCE.turnOn()
        } catch (e: Exception) {
            Log.e("AlarmLightBroadcastReceiver", "Could not interact with light.", e)
        }
    }

    override fun otherTasks(alarm: Alarm, context: Context) {
        // nothing to do
    }

}