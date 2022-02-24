package com.example.foody.ui.fragments.grocery


import android.annotation.SuppressLint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.R
import kotlinx.android.synthetic.main.groceryitem.view.*



class TodoAdapter(

    private val todos: MutableList<Add>


) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.groceryitem,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Add) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteDoneTodos():MutableList<Add> {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
        return todos
    }

    private fun toggleStrikeThrough(textgrocery: TextView, isChecked: Boolean) {
        if(isChecked) {
            textgrocery.paintFlags = textgrocery.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            textgrocery.paintFlags = textgrocery.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            textgrocery.text = curTodo.title
            doneChek.isChecked = curTodo.isChecked
            toggleStrikeThrough(textgrocery, curTodo.isChecked)
            doneChek.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(textgrocery, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}