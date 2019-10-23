package stijnhooft.be.wakeuplight.backend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm (

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    var hours: Int,
    var minutes: Int,
    var enabled: Boolean
)