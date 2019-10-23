package stijnhooft.be.wakeuplight.backend.repository

import android.content.Context
import androidx.lifecycle.LiveData
import stijnhooft.be.wakeuplight.backend.AlarmDatabase
import stijnhooft.be.wakeuplight.backend.domain.Alarm

class AlarmRepository(application: Context) {

    val alarmDao = AlarmDatabase.getDatabase(application).alarmDao()
    val all: LiveData<List<Alarm>> = alarmDao.findAll()

    suspend fun create(alarm: Alarm) {
        alarmDao.create(alarm)
    }

    suspend fun delete(alarm: Alarm) {
        alarmDao.delete(alarm)
    }

    suspend fun update(alarm: Alarm) {
        alarmDao.update(alarm);
    }

}