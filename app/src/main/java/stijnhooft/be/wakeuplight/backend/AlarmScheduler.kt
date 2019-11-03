package stijnhooft.be.wakeuplight.backend

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
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

        val upcomingDateInMilliseconds =
            AlarmUtil.getUpcomingDateInMilliseconds(alarm.hours, alarm.minutes)

        // to get the alarm firing at an exact time, I set two alarms

        // alarm 1
        val info = AlarmClockInfo(upcomingDateInMilliseconds, pendingIntent)
        alarmManager.setAlarmClock(info, pendingIntent)

        // alarm 2
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            upcomingDateInMilliseconds,
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