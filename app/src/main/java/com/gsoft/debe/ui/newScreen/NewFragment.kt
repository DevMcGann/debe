package com.gsoft.debe.ui.newScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gsoft.debe.R
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.databinding.FragmentNewBinding
import com.gsoft.debe.ui.mainViewModel.MainViewModel
import com.gsoft.debe.utils.Resultado
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : Fragment(R.layout.fragment_new), listAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNewBinding
    private lateinit var newAdapter: listAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newAdapter = listAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewBinding.bind(view)
        setView()
    }

    private fun setView() {

        binding.btnAddPatient.setOnClickListener {
            if (binding.etDni.text.isNullOrEmpty() || binding.etNombre.text.isNullOrEmpty() || binding.etCirugia.text.isNullOrEmpty()) {
                Snackbar.make(it, "Los campos no pueden estar vacios", Snackbar.LENGTH_LONG).show()
            } else {
                if (viewModel.listaUrisEstudios.isNullOrEmpty()){
                  // viewModel.estudiosUploadReady = true
                    finalizarNuevoPaciente()
                }else{
                    viewModel.uploadEstudiosToStorageAndGetURL().observe(viewLifecycleOwner, Observer { result ->
                       when (result){
                           is Resultado.Loading -> {
                               viewModel.estudiosUploadReady = false
                               Log.d("CREAR", "estudios LOADING -")
                               isLoading(true)
                           }
                           is Resultado.Success -> {
                               viewModel.setImagenesEstudiosURL(result.data)
                               Log.d("CREAR", "estudios SUCCESS -> ${result.data}")
                               if(viewModel.getImagenesEstudios().isNotEmpty()){
                                   Log.d("CREAR", "getImagenesEstudios es no empty ${viewModel.getImagenesEstudios()}")
                                   isLoading(false)
                                   finalizarNuevoPaciente()
                                   //viewModel.estudiosUploadReady = true
                                   //viewModel.checkCanUploadPatient()
                               }
                           }
                           is Resultado.Failure -> {
                               viewModel.estudiosUploadReady = false
                               isLoading(false)
                               Toast.makeText(requireContext(), "Error cargando Imágenes!", Toast.LENGTH_LONG).show()
                           }
                       }
                    })
                }//subir estudios
                /*if (viewModel.listaUrisLaboratorio.isNullOrEmpty()){
                    viewModel.laboratorioUploadReady = true
                }else{
                    viewModel.uploadLaboratorioToStorageAndGetURL().observe(viewLifecycleOwner, Observer { result ->
                        when (result){
                            is Resultado.Loading -> {
                                viewModel.laboratorioUploadReady = false
                                Log.d("CREAR", "Laboratorio LOADING -")
                                isLoading(true)
                            }
                            is Resultado.Success -> {
                                viewModel.setImagenesLaboratorioURL(result.data)
                                Log.d("CREAR", "lab SUCCESS -> ${result.data}")
                                if(viewModel.getImagenesLaboratorio().isNotEmpty()){
                                    Log.d("CREAR", "getImagenesLab es no empty ${viewModel.getImagenesLaboratorio()}")
                                    isLoading(false)
                                    viewModel.laboratorioUploadReady = true
                                    viewModel.checkCanUploadPatient()
                                }
                            }
                            is Resultado.Failure -> {
                                viewModel.laboratorioUploadReady = false
                                isLoading(false)
                                Toast.makeText(requireContext(), "Error cargando Imágenes!", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }//subir lab*/
                /*viewModel.canCreateNewPatient.observe(viewLifecycleOwner, {
                    it.getContentIfNotHandled()?.let {
                        viewModel.dni = binding.etDni.text.toString()
                        viewModel.nombre=binding.etNombre.text.toString()
                        viewModel.cirugia=binding.etCirugia.text.toString()
                        viewModel.agregarPaciente()
                        Toast.makeText(requireContext(), "Paciente creado exitosamente!", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_newFragment_to_homeFragment)
                    }
                })*/
            }//main if
        }

        binding.imgEstudios.setOnClickListener {
            findNavController().navigate(R.id.action_newFragment_to_studiesFragment)
        }

       /* binding.imgLaboratorio.setOnClickListener {
            findNavController().navigate(R.id.action_newFragment_to_laboratoryFragment)
        }*/

        binding.imgNotas.setOnClickListener {
            findNavController().navigate(R.id.action_newFragment_to_noteFragment)
        }

    }


    override fun onItemClick(item: String, position: Int) {
        newAdapter.deleteItem(position)
    }

    private fun finalizarNuevoPaciente(){
        viewModel.dni = binding.etDni.text.toString()
        viewModel.nombre=binding.etNombre.text.toString()
        viewModel.cirugia=binding.etCirugia.text.toString()
        viewModel.agregarPaciente()
        Toast.makeText(requireContext(), "Paciente creado exitosamente!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_newFragment_to_homeFragment)
    }

    private fun isLoading(loading:Boolean){
        if(loading){
            binding.tvLoading.isVisible = true
            binding.newProgressbar.isVisible = true
            binding.imgNotas.isVisible = false
           /* binding.imgLaboratorio.isVisible = false*/
            binding.imgEstudios.isVisible = false
            binding.textView.isVisible = false
           /* binding.textView2.isVisible = false*/
            binding.textView3.isVisible= false
        }else{
            binding.tvLoading.isVisible = false
            binding.newProgressbar.isVisible = false
            binding.imgNotas.isVisible = true
           /* binding.imgLaboratorio.isVisible = true*/
            binding.imgEstudios.isVisible = true
            binding.textView.isVisible = true
           /* binding.textView2.isVisible = true*/
            binding.textView3.isVisible= true
        }
    }
}





