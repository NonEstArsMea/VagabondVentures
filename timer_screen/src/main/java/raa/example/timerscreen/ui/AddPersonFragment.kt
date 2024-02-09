package raa.example.timerscreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import raa.example.timer_screen.databinding.FragmentAddPersonBinding
import raa.example.timerscreen.domain.PersonParam


class AddPersonFragment : Fragment(), AddPersonDialogFragment.DialogListener {

    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!

    private val mainParamAdapter = RecycleViewAdapter()

    private val viewModel: AddPersonFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAddPerson = binding.recycleView
        rvAddPerson.adapter = mainParamAdapter
        rvAddPerson.layoutManager = LinearLayoutManager(context)

        viewModel.updateList()

        binding.fragmentAddPersonButton.setOnClickListener {
            val dialogFragment = AddPersonDialogFragment()
            dialogFragment.setDialogListener(this)
            dialogFragment.show(requireActivity().supportFragmentManager, "my_dialog")
        }

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.list.observe(viewLifecycleOwner){
            mainParamAdapter.submitList(it)
        }

        mainParamAdapter.onClickListener = {
            viewModel.setSelected(it.id)
        }

        mainParamAdapter.onLongClickListener = {
            viewModel.deleteItem(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPositiveClick(param: PersonParam) {
            viewModel.setParam(param)
    }

    override fun onNegativeClick() {

    }

    companion object {
        @JvmStatic
        fun newInstance(): AddPersonFragment{
            return AddPersonFragment()
        }

    }
}