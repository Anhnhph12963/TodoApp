package com.anhnh.todoapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.anhnh.todoapp.utils.model.ToDoData
import com.anhnh.todoapp.data.model.Note

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: ToDoData)

    @Update
    fun updateTodo(todo: ToDoData)

    @Delete
    fun deleteTodo(todo: ToDoData)

    @Query("SELECT * FROM todos ORDER BY taskId DESC ")
    fun getAllTodo(): LiveData<List<ToDoData>>


}