package stijnhooft.be.wakeuplight.ui.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import stijnhooft.be.wakeuplight.AlarmUtil
import stijnhooft.be.wakeuplight.R
import stijnhooft.be.wakeuplight.backend.domain.Alarm
import stijnhooft.be.wakeuplight.ui.viewmodel.AlarmViewModel


class AlarmViewAdapter internal constructor(
    context: AppCompatActivity,
    private val alarmViewModel: AlarmViewModel
) : RecyclerView.Adapter<AlarmViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var alarms = emptyList<Alarm>() // cached copy of alarms

    init {
        alarmViewModel.allAlarms.observe(context, Observer { alarms ->
            // Update the cached copy of the alarms in the adapter.
            alarms?.let{ setAlarms(it) }
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

    private fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return alarms.size
    }
}

