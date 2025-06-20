package com.example.smartaid.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history_table ORDER BY timestamp DESC")
    fun getAllHistory(): LiveData<List<HistoryEntity>>

    @Insert
    suspend fun insert(history: HistoryEntity)

    @Delete
    suspend fun delete(history: HistoryEntity)

    @Query("DELETE FROM history_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}