package space.lobanovi.taskapp.ui.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import space.lobanovi.taskapp.R
import space.lobanovi.taskapp.databinding.FragmentOnBoardBinding
import space.lobanovi.taskapp.databinding.FragmentOnBoardPageBinding


class OnBoardPageFragment(
    var listenerSkip: () -> Unit,
    var listenerNext: () -> Unit,
) : Fragment() {


    private lateinit var binding: FragmentOnBoardPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPageBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }


    private fun initViews() {
        arguments.let {
            val data = it?.getSerializable("onBoard") as BoardModel
            binding.tvTitleBoard.text = data.title
            binding.tvDeskBoard.text = data.description
            data.img?.toInt()?.let { it1 -> binding.imgBoard.setImageResource(it1) }
            binding.btnSkip.isVisible = data.isLast == false
            binding.btnNext.isVisible = data.isLast == false
            binding.btnStart.isVisible = data.isLast == true
        }
    }

    private fun initListeners() {

        binding.btnNext.setOnClickListener{
            listenerNext.invoke()

        }

        binding.btnSkip.setOnClickListener{
            listenerSkip.invoke()

        }

        binding.btnStart.setOnClickListener {
            (findNavController().navigate(R.id.navigation_home))

        }
    }
}

