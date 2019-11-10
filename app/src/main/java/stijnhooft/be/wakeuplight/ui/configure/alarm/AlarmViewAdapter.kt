package stijnhooft.be.wakeuplight.ui.configure.alarm

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import stijnhooft.be.wakeuplight.AlarmUtil
import stijnhooft.be.wakeuplight.R
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import stijnhooft.be.wakeuplight.ui.configure.AlarmViewModel


class AlarmViewAdapter internal constructor(
    private val context: AppCompatActivity,
    private val alarmViewModel: AlarmViewModel
) : RecyclerView.Adapter<AlarmViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var alarms = emptyList<Alarm>() // cached copy of alarms

    init {
        alarmViewModel.allAlarms.observe(context, Observer { alarms ->
            // Update the cached copy of the alarms in the adapter.
            alarms?.let { setAlarms(it) }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        // create a new view
        val view = inflater.inflate(R.layout.alarm_row, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val current = alarms[position]

        initTimePicker(holder, current)
        initEnabledSwitch(holder, current)
        initDaysButtons(holder, current)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun remove(index: Int) {
        alarmViewModel.delete(alarms[index])
        this.notifyItemRemoved(index)
    }

    private fun initEnabledSwitch(
        holder: AlarmViewHolder,
        current: Alarm
    ) {
        holder.enabled.isChecked = current.enabled
        holder.enabled.setOnCheckedChangeListener { _, isChecked ->
            run {
                current.enabled = isChecked
                alarmViewModel.update(current)
            }
        }
    }

    private fun initTimePicker(
        holder: AlarmViewHolder,
        alarm: Alarm
    ) {
        holder.time.text = AlarmUtil.toString(alarm.hours, alarm.minutes)
    }

    private fun initDaysButtons(
        holder: AlarmViewHolder,
        current: Alarm
    ) {
        updateTextColorOfDay(holder.monday, current.enabledOnMonday)
        holder.monday.setOnClickListener {
            run {
                current.enabledOnMonday = !current.enabledOnMonday
                alarmViewModel.update(current)
            }
        }

        updateTextColorOfDay(holder.tuesday, current.enabledOnTuesday)
        holder.tuesday.setOnClickListener {
            run {
                current.enabledOnTuesday = !current.enabledOnTuesday
                alarmViewModel.update(current)
            }
        }

        updateTextColorOfDay(holder.wednesday, current.enabledOnWednesday)
        holder.wednesday.setOnClickListener {
            run {
                current.enabledOnWednesday = !current.enabledOnWednesday
                alarmViewModel.update(current)
            }
        }

        updateTextColorOfDay(holder.thursday, current.enabledOnThursday)
        holder.thursday.setOnClickListener {
            run {
                current.enabledOnThursday = !current.enabledOnThursday
                alarmViewModel.update(current)
            }
        }

        updateTextColorOfDay(holder.friday, current.enabledOnFriday)
        holder.friday.setOnClickListener {
            run {
                current.enabledOnFriday = !current.enabledOnFriday
                alarmViewModel.update(current)
            }
        }

        updateTextColorOfDay(holder.saturday, current.enabledOnSaturday)
        holder.saturday.setOnClickListener {
            run {
                current.enabledOnSaturday = !current.enabledOnSaturday
                alarmViewModel.update(current)
            }
        }

        updateTextColorOfDay(holder.sunday, current.enabledOnSunday)
        holder.sunday.setOnClickListener {
            run {
                current.enabledOnSunday = !current.enabledOnSunday
                alarmViewModel.update(current)
            }
        }

    }

    private fun updateTextColorOfDay(day: TextView, active: Boolean) {
        if (active) {
            day.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        } else {
            day.setTextColor(Color.GRAY)
        }
    }

    private fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        this.notifyDataSetChanged()
    }

}

