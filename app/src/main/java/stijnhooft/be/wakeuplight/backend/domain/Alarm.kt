package stijnhooft.be.wakeuplight.backend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDate

@Entity
data class Alarm (

    @PrimaryKey
    var id: Long,

    var hours: Int,
    var minutes: Int,
    var enabled: Boolean // TODO differentiate between permanently disabled and temporarily disabled
) {
    fun shouldFireOn(date: LocalDate): Boolean {
        return enabled // TODO check day of week and check temporarily disabled
    }
}