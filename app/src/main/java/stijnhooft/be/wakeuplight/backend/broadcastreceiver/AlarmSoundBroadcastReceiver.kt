package stijnhooft.be.wakeuplight.backend.broadcastreceiver

import android.content.Context
import android.content.Intent
import stijnhooft.be.wakeuplight.backend.AlarmScheduler
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import stijnhooft.be.wakeuplight.ui.fire.FiringAlarmActivity


class AlarmSoundBroadcastReceiver : AbstractAlarmBroadcastReceiver() {

    override fun otherTasks(alarm: Alarm, context: Context) {
        rescheduleAlarmForTomorrow(alarm, context)
    }

    override fun fireAlarm(alarm: Alarm, context: Context) {
        val firingAlarmActivityIntent = Intent(context, FiringAlarmActivity::class.java)
        firingAlarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        firingAlarmActivityIntent.putExtra("alarmId", alarm.id)
        context.startActivity(firingAlarmActivityIntent)
    }

    private fun rescheduleAlarmForTomorrow(alarm: Alarm, context: Context) {
        val alarmScheduler = AlarmScheduler(context)
        alarmScheduler.schedule(alarm)
    }

}