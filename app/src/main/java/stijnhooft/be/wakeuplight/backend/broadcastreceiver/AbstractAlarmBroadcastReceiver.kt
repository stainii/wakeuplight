package stijnhooft.be.wakeuplight.backend.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import stijnhooft.be.wakeuplight.backend.AlarmScheduler
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import stijnhooft.be.wakeuplight.backend.repository.AlarmRepository


abstract class AbstractAlarmBroadcastReceiver : BroadcastReceiver() {

    private lateinit var alarmRepository: AlarmRepository
    private lateinit var alarmScheduler: AlarmScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        // input validation
        requireNotNull(context)

        val alarmId = intent?.extras?.getLong("alarmId")
        requireNotNull(alarmId)

        // initialize class attributes
        this.alarmRepository = AlarmRepository(context)
        this.alarmScheduler = AlarmScheduler(context)

        CoroutineScope(Dispatchers.IO).launch {
            val alarm = alarmRepository.findById(alarmId)
            if (alarm.shouldFireOn(LocalDate())) {
                fireAlarm(alarm, context)
            }
            otherTasks(alarm, context)
        }
    }

    abstract fun fireAlarm(alarm: Alarm, context: Context)

    abstract fun otherTasks(alarm: Alarm, context: Context)

}