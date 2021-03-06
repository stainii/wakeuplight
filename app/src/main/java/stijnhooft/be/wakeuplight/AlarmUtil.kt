package stijnhooft.be.wakeuplight

import org.joda.time.DateTime

class AlarmUtil {

    companion object {
        fun toString(hours: Int, minutes: Int): String {
            val hoursString = if (hours < 10) "0$hours" else "$hours"
            val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
            return "$hoursString:$minutesString"
        }

        fun getUpcomingDateInMilliseconds(alarmHours: Int, alarmMinutes: Int): Long {
            return getUpcomingDateInMilliseconds(alarmHours, alarmMinutes, DateTime())
        }

        fun getUpcomingDateInMilliseconds(alarmHours: Int, alarmMinutes: Int, now: DateTime): Long {
            val nextAlarmCandidate = now.withHourOfDay(alarmHours)
                                .withMinuteOfHour(alarmMinutes)
                                .withSecondOfMinute(0)

            return if (nextAlarmCandidate.isAfter(now)) {
                nextAlarmCandidate.millis
            } else {
                nextAlarmCandidate.plusDays(1).millis
            }
        }
    }
}
