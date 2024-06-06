package com.anhnh.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.anhnh.todoapp.utils.model.ToDoData
import com.anhnh.todoapp.data.model.Note
import com.anhnh.todoapp.data.source.local.AppDatabase

class TodoRepository(private val appDatabase: AppDatabase) {
     fun insertTodo(todo: ToDoData) = appDatabase.todoDao().insertTodo(todo)
    fun editTodo(todo: ToDoData) = appDatabase.todoDao().updateTodo(todo)
      fun deleteTodo(todo: ToDoData) =  appDatabase.todoDao().deleteTodo(todo)
    fun getAllTodo(): LiveData<List<ToDoData>> = appDatabase.todoDao().getAllTodo()

}