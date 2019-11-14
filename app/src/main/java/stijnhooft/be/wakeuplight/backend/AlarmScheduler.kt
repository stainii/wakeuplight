package stijnhooft.be.wakeuplight.backend

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import stijnhooft.be.wakeuplight.AlarmUtil
import stijnhooft.be.wakeuplight.backend.broadcastreceiver.AlarmLightBroadcastReceiver
import stijnhooft.be.wakeuplight.backend.broadcastreceiver.AlarmSoundBroadcastReceiver
import stijnhooft.be.wakeuplight.backend.broadcastreceiver.TurnOffAlarmLightBroadcastReceiver
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import java.util.*


class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(alarm: Alarm) {
        Log.i("AlarmScheduler", "Scheduling alarm $alarm")

        val pendingIntentAlarmSound = createPendingIntentForAlarmSound(alarm)
        val upcomingDateInMilliseconds =
            AlarmUtil.getUpcomingDateInMilliseconds(alarm.hours, alarm.minutes)
        setAlarm(upcomingDateInMilliseconds, pendingIntentAlarmSound)

        val pendingIntentAlarmLight = createPendingIntentForAlarmLight(alarm)
        val upcomingDateInMillisecondsMinusFiveMinutes = upcomingDateInMilliseconds - 300_000
        setAlarm(upcomingDateInMillisecondsMinusFiveMinutes, pendingIntentAlarmLight)
    }

    fun turnOffLightInAMinute() {
        val intent = Intent(context, TurnOffAlarmLightBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, Random().nextInt(), intent, 0)
        setAlarm(AlarmUtil.getNowPlusXMinutesInMilliseconds(1), pendingIntent)
    }

    fun cancel(alarm: Alarm) {
        val pendingIntentForAlarmLight = createPendingIntentForAlarmLight(alarm)
        val pendingIntentForAlarmSound = createPendingIntentForAlarmSound(alarm)

        if (pendingIntentForAlarmLight != null) {
            alarmManager.cancel(pendingIntentForAlarmLight)
        }

        if (pendingIntentForAlarmSound != null) {
            alarmManager.cancel(pendingIntentForAlarmSound)
        }
    }

    private fun createPendingIntentForAlarmLight(alarm: Alarm): PendingIntent? {
        val intent = Intent(context, AlarmLightBroadcastReceiver::class.java)
        intent.putExtra("alarmId", alarm.id)

        return PendingIntent.getBroadcast(context, alarm.id.toInt(), intent, 0)
    }

    private fun createPendingIntentForAlarmSound(alarm: Alarm): PendingIntent? {
        val intent = Intent(context, AlarmSoundBroadcastReceiver::class.java)
        intent.putExtra("alarmId", alarm.id)

        return PendingIntent.getBroadcast(context, -alarm.id.toInt(), intent, 0)
    }

    private fun setAlarm(
        upcomingDateInMilliseconds: Long,
        pendingIntentAlarmSound: PendingIntent?
    ) {
        // to get the alarm firing at an exact time, I set two alarms

        // alarm 1
        val info = AlarmClockInfo(upcomingDateInMilliseconds, pendingIntentAlarmSound)
        alarmManager.setAlarmClock(info, pendingIntentAlarmSound)

        // alarm 2
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            upcomingDateInMilliseconds,
            pendingIntentAlarmSound
        )
    }
}