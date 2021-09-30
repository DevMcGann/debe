package com.gsoft.debe.ui.searchScreen


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsoft.debe.R
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.databinding.FragmentSearchBinding
import com.gsoft.debe.ui.newScreen.listAdapter
import com.gsoft.debe.ui.newScreen.newViewModel
import com.gsoft.debe.utils.Resultado
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) , listAdapter.OnItemClickListener {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var newAdapter: listAdapter
    private val viewModel: newViewModel by viewModels()

    private var listadeItems : MutableList<String> = mutableListOf()
    private var currentPaciente = Paciente()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newAdapter = listAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = newAdapter
        newAdapter.setListaDeItems(listadeItems)

        binding.bActualizar.isVisible = false
        binding.bAgregarItemSearch.isVisible = false
        binding.bEliminar.isVisible = false

        //setView()
    }

    /*private fun setView(){
        binding.bBuscarPaciente.setOnClickListener {
            val dni = binding.etBuscar.text
            viewModel.fetchPaciente(dni.toString()).observe(viewLifecycleOwner, Observer { result ->
                when (result) {

                    is Resultado.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is Resultado.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        if(result.data == null){
                            resetScreen()
                            binding.tvPaciente.text = "No se encontró Paciente con ese DNI"
                        }
                        val paciente : Paciente = result.data as Paciente
                        binding.tvPaciente.text = paciente?.nombre
                        paciente?.items?.let { it1 ->
                            newAdapter.setListaDeItems(it1)
                            listadeItems = it1
                        }
                        binding.bEliminar.isVisible = true
                        binding.bActualizar.isVisible = true
                        binding.bAgregarItemSearch.isVisible = true
                       currentPaciente = paciente

                    }

                    is Resultado.Failure -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Hubo un error : ${result.exception}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("ERROR", result.exception.toString())

                    }
                }
            })
        }

        binding.bAgregarItemSearch.setOnClickListener {
            showdialog()
        }

        binding.bActualizar.setOnClickListener {
            try {
                viewModel.updatePaciente(currentPaciente)
                Toast.makeText(
                    requireContext(),
                    "Paciente Actualizado",
                    Toast.LENGTH_LONG
                ).show()
            }catch (e:Exception){
                Toast.makeText(
                    requireContext(),
                    "Hubo un error",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        binding.bEliminar.setOnClickListener {
            try {
                viewModel.deletePaciente(currentPaciente)
                Toast.makeText(
                    requireContext(),
                    "Eliminado",
                    Toast.LENGTH_LONG
                ).show()
                resetScreen()
            }catch (e:Exception){
                Toast.makeText(
                    requireContext(),
                    "Hubo un error",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }*/

    fun showdialog(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builder.setTitle("Agregar Item")
        val input = EditText(this.context)
        input.setHint("Item")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            val itemNuevo = input.text.toString()
            listadeItems.add(itemNuevo)

        })
        builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }

    override fun onItemClick(item: String, position: Int) {
        newAdapter.deleteItem(position)

    }


    fun resetScreen(){
        listadeItems = mutableListOf()
        binding.tvPaciente.text=""
        binding.bAgregarItemSearch.isVisible = false
        binding.bActualizar.isVisible = false
        binding.bEliminar.isVisible = false
        currentPaciente = Paciente()
        newAdapter.setListaDeItems(listadeItems)
    }


}