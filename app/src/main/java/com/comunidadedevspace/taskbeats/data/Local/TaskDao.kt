package com.comunidadedevspace.taskbeats.data.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface  TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)


    @Query("select * from task")
    fun getAll():LiveData<List<Task>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: Task)

    @Query("DELETE from task")
    suspend fun deleteAll()

    @Query("DELETE from task where id =:id")
    suspend fun deleteById(id:Int)

}