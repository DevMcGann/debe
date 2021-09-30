package com.gsoft.debe.ui.studiesScreen

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gsoft.debe.R
import com.gsoft.debe.databinding.FragmentNewBinding
import com.gsoft.debe.databinding.FragmentStudiesBinding
import com.gsoft.debe.ui.categoryAdapters.URI_Adapter
import com.gsoft.debe.ui.categoryAdapters.URL_Adapter
import com.gsoft.debe.ui.mainViewModel.MainViewModel
import com.gsoft.debe.ui.newScreen.listAdapter
import com.gsoft.debe.ui.newScreen.newViewModel
import com.gsoft.debe.utils.Resultado
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class studiesFragment : Fragment(R.layout.fragment_studies), URI_Adapter.OnItemClickListener  {

    private lateinit var binding : FragmentStudiesBinding
    private lateinit var studiesAdapterURI: URI_Adapter
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studiesAdapterURI = URI_Adapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudiesBinding.bind(view)
        binding.rvStudies.adapter = studiesAdapterURI
        setView()
        observeViewModel()
    }


    private fun setView() {
        binding.btnAddStudies.setOnClickListener {
            viewModel.categoriaSeleccionada = "estudios"
            findNavController().navigate(R.id.action_studiesFragment_to_pickImagenes)
        }

        studiesAdapterURI.setListaDeItems(viewModel.listaUrisEstudios)
    }


    private fun observeViewModel() {

    }

    override fun onItemClick(item: Uri, position: Int) {
        //viewModel.listaUrisEstudios.remove(item)
    }
}

