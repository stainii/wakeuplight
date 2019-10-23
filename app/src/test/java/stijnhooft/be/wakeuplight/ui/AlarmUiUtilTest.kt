package stijnhooft.be.wakeuplight.ui

import org.junit.Assert.*
import org.junit.Test

class AlarmUiUtilTest {

    @Test
    fun eightOClockToString() {
       assertEquals("08:00", AlarmUiUtil.toString(8, 0));
    }

    @Test
    fun thirteenOClockToString() {
        assertEquals("13:00", AlarmUiUtil.toString(13, 0))
    }

    @Test
    fun twoHoursThirtyMinutesToString() {
        assertEquals("02:30", AlarmUiUtil.toString(2, 30))
    }

    @Test
    fun twentyOnePastTwelveToString() {
        assertEquals("12:21", AlarmUiUtil.toString(12, 21))
    }

}