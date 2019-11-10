package stijnhooft.be.wakeuplight.backend.domain

import org.joda.time.LocalDate
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AlarmTest {

    @Test
    fun shouldFireWhenDisabled() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = false,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 6)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireWhenTemporarilyDisabled() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = false,
            temporarilyDisabled = true,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 6)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnMonday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = false,
            enabledOnWednesday = false,
            enabledOnThursday = false,
            enabledOnFriday = false,
            enabledOnSaturday = false,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 4)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnMonday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 4)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnTuesday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = true,
            enabledOnWednesday = false,
            enabledOnThursday = false,
            enabledOnFriday = false,
            enabledOnSaturday = false,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 5)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnTuesday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = false,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 5)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnWednesday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = false,
            enabledOnWednesday = true,
            enabledOnThursday = false,
            enabledOnFriday = false,
            enabledOnSaturday = false,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 6)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnWednesday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = false,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 6)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnThursday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = false,
            enabledOnWednesday = false,
            enabledOnThursday = true,
            enabledOnFriday = false,
            enabledOnSaturday = false,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 7)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnThursday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = false,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 7)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnFriday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = false,
            enabledOnWednesday = false,
            enabledOnThursday = false,
            enabledOnFriday = true,
            enabledOnSaturday = false,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 8)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnFriday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = false,
            enabledOnSaturday = true,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 8)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnSaturday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = false,
            enabledOnWednesday = false,
            enabledOnThursday = false,
            enabledOnFriday = false,
            enabledOnSaturday = true,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 9)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnSaturday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = false,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 9)

        assertFalse(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenEnabledOnSunday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = false,
            enabledOnTuesday = false,
            enabledOnWednesday = false,
            enabledOnThursday = false,
            enabledOnFriday = false,
            enabledOnSaturday = false,
            enabledOnSunday = true
        )
        val date = LocalDate(2019, 11, 10)

        assertTrue(alarm.shouldFireOn(date))
    }

    @Test
    fun shouldFireOnMondayWhenDisabledOnSunday() {
        val alarm = Alarm(
            id = 1,
            hours = 7,
            minutes = 0,
            enabled = true,
            temporarilyDisabled = false,
            enabledOnMonday = true,
            enabledOnTuesday = true,
            enabledOnWednesday = true,
            enabledOnThursday = true,
            enabledOnFriday = true,
            enabledOnSaturday = true,
            enabledOnSunday = false
        )
        val date = LocalDate(2019, 11, 10)

        assertFalse(alarm.shouldFireOn(date))
    }

}