package stijnhooft.be.wakeuplight.backend.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDate

@Entity
data class Alarm(

    @PrimaryKey
    var id: Long,

    var hours: Int,
    var minutes: Int,

    var enabled: Boolean,

    @ColumnInfo(name = "monday")
    var enabledOnMonday: Boolean,

    @ColumnInfo(name = "tuesday")
    var enabledOnTuesday: Boolean,

    @ColumnInfo(name = "wednesday")
    var enabledOnWednesday: Boolean,

    @ColumnInfo(name = "thursday")
    var enabledOnThursday: Boolean,

    @ColumnInfo(name = "friday")
    var enabledOnFriday: Boolean,

    @ColumnInfo(name = "saturday")
    var enabledOnSaturday: Boolean,

    @ColumnInfo(name = "sunday")
    var enabledOnSunday: Boolean


) {
    fun shouldFireOn(date: LocalDate): Boolean {
        return enabled
                && when (date.dayOfWeek) {
            1 -> enabledOnMonday
            2 -> enabledOnTuesday
            3 -> enabledOnWednesday
            4 -> enabledOnThursday
            5 -> enabledOnFriday
            6 -> enabledOnSaturday
            7 -> enabledOnSunday
            else -> throw UnsupportedOperationException("Date has an invalid day of week: " + date.dayOfWeek)
        }
    }
}