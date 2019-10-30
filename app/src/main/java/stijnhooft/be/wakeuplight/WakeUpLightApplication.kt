package stijnhooft.be.wakeuplight

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid


class WakeUpLightApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}