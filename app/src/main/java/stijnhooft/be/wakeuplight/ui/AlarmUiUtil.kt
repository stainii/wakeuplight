package stijnhooft.be.wakeuplight.ui

class AlarmUiUtil {

    companion object {
        fun toString(hours: Int, minutes: Int): String {
            val hoursString = if (hours < 10) "0$hours" else "$hours"
            val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
            return "$hoursString:$minutesString"
        }
    }
}
