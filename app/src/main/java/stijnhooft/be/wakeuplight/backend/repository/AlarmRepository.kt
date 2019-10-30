package stijnhooft.be.wakeuplight.backend.repository

import android.content.Context
import androidx.lifecycle.LiveData
import stijnhooft.be.wakeuplight.backend.AlarmDatabase
import stijnhooft.be.wakeuplight.backend.domain.Alarm

class AlarmRepository(application: Context) {

    private val alarmDao = AlarmDatabase.getDatabase(application).alarmDao()
    val observeAll: LiveData<List<Alarm>> = alarmDao.observeAll()

    suspend fun findById(id : Long): Alarm {
        return alarmDao.findById(id)
    }

    suspend fun findAll(): List<Alarm> {
        return alarmDao.findAll()
    }

    suspend fun create(alarm: Alarm) {
        alarmDao.create(alarm)
    }

    suspend fun delete(alarm: Alarm) {
        alarmDao.delete(alarm)
    }

    suspend fun update(alarm: Alarm) {
        alarmDao.update(alarm)
    }

}