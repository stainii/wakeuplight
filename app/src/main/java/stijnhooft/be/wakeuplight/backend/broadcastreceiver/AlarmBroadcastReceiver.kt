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
import stijnhooft.be.wakeuplight.ui.FiringAlarmActivity


class AlarmBroadcastReceiver : BroadcastReceiver() {

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
            fireAlarmWhenNecessary(alarm, context)
            rescheduleAlarmForTomorrow(alarm)
        }
    }

    private fun fireAlarmWhenNecessary(alarm: Alarm, context: Context) {
        if (alarm.shouldFireOn(LocalDate())) {
            startFiringAlarmActivity(context, alarm.id)
        }
    }

    private fun rescheduleAlarmForTomorrow(alarm: Alarm) {
        this.alarmScheduler.schedule(alarm)
    }

    private fun startFiringAlarmActivity(
        context: Context,
        alarmId: Long
    ) {
        val firingAlarmActivityIntent = Intent(context, FiringAlarmActivity::class.java)
        firingAlarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        firingAlarmActivityIntent.putExtra("alarmId", alarmId)
        context.startActivity(firingAlarmActivityIntent)
    }

}