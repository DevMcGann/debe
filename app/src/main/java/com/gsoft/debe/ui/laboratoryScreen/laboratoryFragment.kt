package com.gsoft.debe.ui.laboratoryScreen

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gsoft.debe.R
import com.gsoft.debe.databinding.FragmentLaboratoryBinding
import com.gsoft.debe.ui.categoryAdapters.URI_Adapter
import com.gsoft.debe.ui.mainViewModel.MainViewModel

class laboratoryFragment : Fragment(R.layout.fragment_laboratory), URI_Adapter.OnItemClickListener {

    private lateinit var binding : FragmentLaboratoryBinding
    private lateinit var labAdapterURI: URI_Adapter
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        labAdapterURI = URI_Adapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLaboratoryBinding.bind(view)
        binding.rvLaboratory.adapter = labAdapterURI
        setView()
        observeViewModel()
    }

    private fun setView() {
        binding.btnAddLaboratory.setOnClickListener {
            viewModel.categoriaSeleccionada = "laboratorio"
            findNavController().navigate(R.id.action_laboratoryFragment_to_pickImagenes)
        }

        labAdapterURI.setListaDeItems(viewModel.listaUrisLaboratorio)
    }


    private fun observeViewModel() {

    }

    override fun onItemClick(item: Uri, position: Int) {
        TODO("Not yet implemented")
    }

}