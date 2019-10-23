package stijnhooft.be.wakeuplight.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import stijnhooft.be.wakeuplight.backend.repository.AlarmRepository

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    private val alarmRepository: AlarmRepository = AlarmRepository(application)
    val allAlarms: LiveData<List<Alarm>>

    init {
        allAlarms = alarmRepository.all
    }

    fun create(alarm: Alarm) = viewModelScope.launch {
        alarmRepository.create(alarm)
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        alarmRepository.update(alarm)
    }

    fun delete(alarm: Alarm) = viewModelScope.launch {
        alarmRepository.delete(alarm)
    }
}