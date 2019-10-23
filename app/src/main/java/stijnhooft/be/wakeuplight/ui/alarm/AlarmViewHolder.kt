package stijnhooft.be.wakeuplight.ui.alarm

import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stijnhooft.be.wakeuplight.R

class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val time: TextView = itemView.findViewById(R.id.time)
    val enabled: Switch = itemView.findViewById(R.id.enabled)
}