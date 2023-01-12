package space.lobanovi.taskapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import space.lobanovi.taskapp.R
import space.lobanovi.taskapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        initViews()
        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alert()
    }

    private fun alert() {
        taskAdapter.onLongClick = {

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Удалить эту новость")
            dialog.setMessage("Вы точно хотите удалить эту новость?")
            dialog.setPositiveButton("Да") { _, _ ->

                taskAdapter.deleteItemsAndNotifyAdapter(it)
                binding.rvHome.adapter = taskAdapter
                //Delete items in RecyclerView**

            }
            dialog.setNegativeButton("Назад") { dialog, _ -> dialog.cancel() }
            dialog.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = TaskAdapter()
    }

    private fun initListeners() {
        binding.fabHome.setOnClickListener{
            findNavController().navigate(R.id.newTaskFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort){
            val items = arrayOf("По Дате", "По Алфавиту")

            val builder = AlertDialog.Builder(requireContext())
            with(builder){
                setTitle("Сортировать по")
                setItems(items){ dialog, which ->
                    when(which){

                        0 ->{

                        }
                        1 ->{

                        }
                    }

                }

                show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViews() {

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        setFragmentResultListener("new_task") { key,bundle->
            val title =  bundle.get("title")
            val description =  bundle.get("desc")
            taskAdapter.addTask(TaskModel(title.toString(),description.toString()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}