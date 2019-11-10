package stijnhooft.be.wakeuplight.ui.configure.alarm

import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stijnhooft.be.wakeuplight.R

class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val time: TextView = itemView.findViewById(R.id.time)
    val enabled: Switch = itemView.findViewById(R.id.enabled)

    val monday: TextView = itemView.findViewById(R.id.monday)
    val tuesday: Button = itemView.findViewById(R.id.tuesday)
    val wednesday: Button = itemView.findViewById(R.id.wednesday)
    val thursday: Button = itemView.findViewById(R.id.thursday)
    val friday: Button = itemView.findViewById(R.id.friday)
    val saturday: Button = itemView.findViewById(R.id.saturday)
    val sunday: Button = itemView.findViewById(R.id.sunday)
}