package stijnhooft.be.wakeuplight.backend.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import stijnhooft.be.wakeuplight.backend.domain.Alarm

@Dao
interface AlarmDao {

    @Query("select * from alarm")
    fun findAll(): LiveData<List<Alarm>>

    @Insert
    suspend fun create(vararg alarms: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("delete from alarm")
    suspend fun deleteAll()


}