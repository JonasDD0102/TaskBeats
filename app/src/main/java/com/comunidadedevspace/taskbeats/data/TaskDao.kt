package com.comunidadedevspace.taskbeats.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.comunidadedevspace.taskbeats.data.Task

@Dao
interface  TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)


    @Query("select * from task")
    fun getAll():List<Task>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    @Query("DELETE from task")
    fun deleteAll()

    @Query("DELETE from task where id =:id")
    fun deleteById(id:Int)

}