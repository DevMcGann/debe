package com.gsoft.debe.ui.newScreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gsoft.debe.R
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.databinding.FragmentHomeBinding
import com.gsoft.debe.databinding.FragmentNewBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class NewFragment : Fragment(R.layout.fragment_new),listAdapter.OnItemClickListener {

    private lateinit var binding : FragmentNewBinding
    private lateinit var newAdapter: listAdapter
    private val viewModel: newViewModel by viewModels()

    private var listadeItems : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newAdapter = listAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewBinding.bind(view)
        binding.rvDebeNuevo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDebeNuevo.adapter = newAdapter
        newAdapter.setListaDeItems(listadeItems)

        setView()
    }

    private fun setView() {

        binding.bAgregarItem.setOnClickListener {
            showdialog()
        }

        binding.bAgregarPaciente.setOnClickListener {
            if (binding.etDni.text.isNullOrEmpty() || binding.etNombre.text.isNullOrEmpty()){
                Snackbar.make(it,"Nombre y Dni no pueden estar vacios",Snackbar.LENGTH_LONG).show()
            }else{
                val paciente = Paciente(
                    id = binding.etDni.text.toString(),
                    dni = binding.etDni.text.toString(),
                    nombre = binding.etNombre.text.toString(),
                    items = listadeItems
                )
                try {
                    viewModel.crearPaciente(paciente)
                    Snackbar.make(it,"Paciente creado",Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_newFragment_to_homeFragment)
                }catch (e:Exception){
                    Snackbar.make(it,"Hubo un error",Snackbar.LENGTH_LONG).show()
                }

            }
        }
    }


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
}





