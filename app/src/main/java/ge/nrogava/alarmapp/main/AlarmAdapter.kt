package ge.nrogava.alarmapp.main
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nrogava.alarmapp.R
import ge.nrogava.alarmapp.data.entity.Alarm

class AlarmAdapter(var alarmListener : MainInteractor.AlarmListListener) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {
    var list = listOf<Alarm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.alarm,parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderTodo: AlarmViewHolder, position: Int) {
        var alarm : Alarm= list[position]
        holderTodo.bindTodo(alarm)
        holderTodo.itemView.setOnLongClickListener{
            alarmListener.onAlarmClicked(alarm)
        }
    }

    inner class AlarmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTodo(alarm : Alarm) {
            txtTime.text = alarm.Time
            active_switch.isChecked = alarm.Active
            active_switch.setOnClickListener {
                alarmListener.onAlarmSwitchClicked(alarm)
            }

        }
        private var active_switch = view.findViewById<Switch>(R.id.switch_active)
        private var txtTime = view.findViewById<TextView>(R.id.txt_time)

    }
}