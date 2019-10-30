package stijnhooft.be.wakeuplight

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class AlarmUtilTest {

    @Test
    fun toString_eightOClock() {
        assertEquals("08:00", AlarmUtil.toString(8, 0))
    }

    @Test
    fun toString_thirteenOClock() {
        assertEquals("13:00", AlarmUtil.toString(13, 0))
    }

    @Test
    fun toString_twoHoursThirtyMinutes() {
        assertEquals("02:30", AlarmUtil.toString(2, 30))
    }

    @Test
    fun toString_twentyOnePastTwelve() {
        assertEquals("12:21", AlarmUtil.toString(12, 21))
    }

    @Test
    fun getUpcomingDateInMilliseconds_alarmWontFireAnymoreToday() {
        val now = DateTime(2019, 10, 10, 15, 13, 0, 0)
        assertEquals(1570799520000, AlarmUtil.getUpcomingDateInMilliseconds(13, 12, now))
    }

    @Test
    fun getUpcomingDateInMilliseconds_alarmWillFireInAnHour() {
        val now = DateTime(2019, 10, 10, 15, 13, 0, 0)

        assertEquals(
            1570723200000,
            AlarmUtil.getUpcomingDateInMilliseconds(16, 0, now)
        )
    }

}