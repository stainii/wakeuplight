package stijnhooft.be.wakeuplight.backend

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import stijnhooft.be.wakeuplight.AlarmUtil
import stijnhooft.be.wakeuplight.backend.broadcastreceiver.AlarmBroadcastReceiver
import stijnhooft.be.wakeuplight.backend.domain.Alarm

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(alarm: Alarm) {
        Log.i("AlarmScheduler", "Scheduling alarm $alarm")
        val pendingIntent = createPendingIntent(alarm)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            AlarmUtil.getUpcomingDateInMilliseconds(alarm.hours, alarm.minutes),
            pendingIntent
        )
    }

    fun cancel(alarm: Alarm) {
        val pendingIntent = createPendingIntent(alarm)
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun createPendingIntent(alarm: Alarm): PendingIntent? {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("alarmId", alarm.id)

        return PendingIntent.getBroadcast(context, alarm.id.toInt(), intent, 0)
    }

}