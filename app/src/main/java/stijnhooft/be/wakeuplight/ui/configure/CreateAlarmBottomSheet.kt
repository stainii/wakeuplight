package stijnhooft.be.wakeuplight.ui.configure

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.joda.time.DateTime
import stijnhooft.be.wakeuplight.R
import stijnhooft.be.wakeuplight.backend.domain.Alarm

class CreateAlarmBottomSheet : BottomSheetDialogFragment() {

    private lateinit var callback: (Alarm) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate view with the same theme as the activity
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        val view = inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.create_alarm_bottom_sheet, container, false)

        // get view objects
        val timePicker = view.findViewById<TimePicker>(R.id.editTime)
        val saveButton = view.findViewById<Button>(R.id.save)

        // set time picker defaults
        timePicker.hour = 7
        timePicker.minute = 0
        timePicker.setIs24HourView(true)

        // add click listener to save button
        saveButton.setOnClickListener {
            val id = DateTime().millis
            callback(Alarm(id, timePicker.hour, timePicker.minute, true))
            this.dismiss()
        }

        return view
    }

    fun setSaveListener(listener: (Alarm) -> Unit) {
        this.callback = listener
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}