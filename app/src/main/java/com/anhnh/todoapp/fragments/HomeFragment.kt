package com.anhnh.todoapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhnh.todoapp.databinding.FragmentHomeBinding
import com.anhnh.todoapp.utils.adapter.TaskAdapter
import com.anhnh.todoapp.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.anhnh.todoapp.data.repository.TodoRepository
import com.anhnh.todoapp.data.source.local.AppDatabase

class HomeFragment : Fragment(), ToDoDialogFragment.OnDialogNextBtnClickListener,
    TaskAdapter.TaskAdapterInterface {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: TodoRepository
    private var frag: ToDoDialogFragment? = null
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var toDoItemList: List<ToDoData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getTaskFromDatabase()

        binding.addTaskBtn.setOnClickListener {

            if (frag != null) childFragmentManager.beginTransaction().remove(frag!!).commit()
            frag = ToDoDialogFragment()
            frag!!.setListener(this)

            frag!!.show(
                childFragmentManager, ToDoDialogFragment.TAG
            )

        }
    }

    private fun getTaskFromDatabase() {
        database.getAllTodo().observe(viewLifecycleOwner) {
            Log.d(TAG, "getTaskFromFirebase: " + it.size)
            toDoItemList = it
            taskAdapter = TaskAdapter(toDoItemList)
            taskAdapter.setListener(this)
            binding.mainRecyclerView.adapter = taskAdapter
            taskAdapter.notifyDataSetChanged()

        }
    }


    private fun init() {
        database = TodoRepository(
            AppDatabase.invoke(requireActivity().application)
        )

        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)

        toDoItemList = mutableListOf()
        taskAdapter = TaskAdapter(toDoItemList)
        taskAdapter.setListener(this)
        binding.mainRecyclerView.adapter = taskAdapter
    }

    override fun saveTask(todoTask: String, todoEdit: TextInputEditText) {
        database.insertTodo(ToDoData(0, todoEdit.text.toString()))
        Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_SHORT).show()
        todoEdit.text = null
        frag!!.dismiss()

    }

    override fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText) {

        database.editTodo(ToDoData(toDoData.taskId, todoEdit.text.toString()))
        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()

        frag!!.dismiss()
    }


    override fun onDeleteItemClicked(toDoData: ToDoData, position: Int) {

        database.deleteTodo(toDoData)
        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onEditItemClicked(toDoData: ToDoData, position: Int) {
        if (frag != null) childFragmentManager.beginTransaction().remove(frag!!).commit()

        frag = ToDoDialogFragment.newInstance(toDoData.taskId!!, toDoData.task)
        frag!!.setListener(this)
        frag!!.show(
            childFragmentManager, ToDoDialogFragment.TAG
        )
    }

}