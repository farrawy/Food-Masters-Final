package com.example.foody.ui.fragments.grocery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foody.databinding.FragmentGroceryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_grocery.*

class GroceryFragment : Fragment() {

    private var _binding: FragmentGroceryBinding? = null
    private val binding get() = _binding!!

    private lateinit var todoAdapter: TodoAdapter
    lateinit var todoList: MutableList<Add>
     lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ?{

        _binding = FragmentGroceryBinding.inflate(inflater, container, false)
        sharedPreferences =requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)



            loadAllTodoList()

        if (todoList.isEmpty()) {
            todoList = mutableListOf()


        }
        todoAdapter = TodoAdapter(todoList)
        binding.rvItems.adapter=todoAdapter


        // add button
       binding.btnAdd.setOnClickListener {
            val todoTitle = eksik_title.text.toString()
            if (todoTitle.isNotEmpty()) {
                val addItem = Add(todoTitle)
                todoList.add(addItem)
                todoAdapter.notifyDataSetChanged()
                eksik_title.text.clear()

            }
            //calling save fun
            saveList(todoList)

        }

        //delete button
      binding.btnDelete.setOnClickListener {
            val newList= todoAdapter.deleteDoneTodos()
            //calling save fun to save the new list after deleting items
            saveList(newList)
        }

        // Inflate the layout for this fragment
        return binding.root
    }




    // saving all items list to the sharedPreferences
    private fun saveList(todoList: MutableList<Add>) {
        val gson = Gson()
        val editor = sharedPreferences.edit()
        //  converting list into Gson string to save in the shared preferences
        val stringList = gson.toJson(todoList)
        editor.putString("All todo list", stringList)
        editor.apply()
    }

    // Get the List from sharedPreferences
    fun loadAllTodoList() {
        val gson = Gson()
        val json = sharedPreferences.getString("All todo list", "")
        if (json.isNullOrEmpty()) {
            todoList = mutableListOf()


        } else {

            todoList = gson.fromJson(
                json, object : TypeToken<List<Add>>() {}.type
            )
        }

    }

}

