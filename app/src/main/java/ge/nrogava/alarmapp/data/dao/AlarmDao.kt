package ge.nrogava.alarmapp.data.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.nrogava.alarmapp.data.entity.Alarm

@Dao
interface AlarmDao {
    @Query("Select * from Alarm")
    fun getAlarmList(): List<Alarm>

    @Insert
    fun addAlarm(alarm: Alarm)

    @Query("Select * from Alarm Where id = :alarmId")
    fun getAlarmDetails(alarmId : Int): Alarm

    @Query("Delete from Alarm Where id = :alarmId")
    fun deleteAlarm(alarmId : Int)

    @Query("delete from Alarm")
    fun deleteAll()


    @Query("select max(id) from Alarm")
    abstract fun getlastAlarmId(): Int

    @Query("update Alarm set Time = :time , Active = :sw where id = :id")
    abstract fun updateAlarm(id: Int, time: String, sw: Boolean)

}