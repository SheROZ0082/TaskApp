package space.lobanovi.taskapp.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.lobanovi.taskapp.databinding.TaskItemBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    fun addTask(taskModel: TaskModel){
        taskList.add(0,taskModel)
        notifyItemChanged(0)
    }

    private var taskList = arrayListOf<TaskModel>()
    var onClick: ((TaskModel) -> Unit)? = null
    var onLongClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(taskList[position])
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)

        }

    }

    override fun getItemCount(): Int = taskList.size
    fun deleteItemsAndNotifyAdapter(pos: Int) {
        taskList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(taskModel: TaskModel) {
            binding.tvTitle.text = taskModel.title
            binding.tvDesc.text = taskModel.description
            itemView.setOnClickListener {
                onClick?.invoke(taskModel)
            }

            itemView.setOnLongClickListener {
                onLongClick?.invoke(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }

}