package stijnhooft.be.wakeuplight.ui.configure

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stijnhooft.be.wakeuplight.backend.AlarmScheduler
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import stijnhooft.be.wakeuplight.backend.repository.AlarmRepository

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    private val alarmRepository: AlarmRepository = AlarmRepository(application)
    private val alarmScheduler: AlarmScheduler
    val allAlarms: LiveData<List<Alarm>>

    init {
        allAlarms = alarmRepository.observeAll
        alarmScheduler = AlarmScheduler(application)
    }

    fun create(alarm: Alarm) = viewModelScope.launch {
        alarmRepository.create(alarm)
        alarmScheduler.schedule(alarm)
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        alarmRepository.update(alarm)
    }

    fun delete(alarm: Alarm) = viewModelScope.launch {
        alarmScheduler.cancel(alarm)
        alarmRepository.delete(alarm)
    }
}