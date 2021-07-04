package ge.nrogava.alarmapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity
data class Alarm(
    @ColumnInfo(name = "Time") var Time: String,
    @ColumnInfo(name = "Active") var Active: Boolean
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
