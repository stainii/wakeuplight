package stijnhooft.be.wakeuplight.backend.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import stijnhooft.be.wakeuplight.backend.AlarmScheduler
import stijnhooft.be.wakeuplight.backend.repository.AlarmRepository

class RescheduleAlarmsAfterRebootBroadcastReceiver : BroadcastReceiver() {

    private lateinit var alarmRepository: AlarmRepository
    private lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        // input validation
        requireNotNull(context)

        // initialize class attributes
        this.alarmRepository = AlarmRepository(context)
        this.alarmScheduler = AlarmScheduler(context)

        // fetch all existing alarms and schedule them
        CoroutineScope(Dispatchers.IO).launch {
            val alarms = alarmRepository.findAll()
            alarms.forEach { alarm -> alarmScheduler.schedule(alarm) }
        }

    }

}