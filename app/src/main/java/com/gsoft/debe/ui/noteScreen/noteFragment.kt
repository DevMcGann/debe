package com.gsoft.debe.ui.noteScreen

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gsoft.debe.R
import com.gsoft.debe.databinding.FragmentLaboratoryBinding
import com.gsoft.debe.databinding.FragmentNoteBinding
import com.gsoft.debe.ui.categoryAdapters.Notes_Adapter
import com.gsoft.debe.ui.categoryAdapters.URI_Adapter
import com.gsoft.debe.ui.mainViewModel.MainViewModel
import okhttp3.internal.notifyAll

class noteFragment : Fragment(R.layout.fragment_note), Notes_Adapter.OnItemClickListener {

    private lateinit var binding : FragmentNoteBinding
    private lateinit var notesAdapter: Notes_Adapter
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesAdapter = Notes_Adapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)
        binding.rvNotes.adapter = notesAdapter
        setView()
        observeViewModel()
    }

    private fun setView() {
        binding.addNote.setOnClickListener {
            showEditTextAlert()
        }

        notesAdapter.setListaDeItems(viewModel.notasList)
    }


    private fun observeViewModel() {

    }

    override fun onItemClick(item: String, position: Int) {
        notesAdapter.notifyItemRemoved(position)
        viewModel.removerNota(item)
    }

    private fun showEditTextAlert() {
        val editText = EditText(requireContext())
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
        editText.setLayoutParams(layoutParams)
        AlertDialog.Builder(requireContext())
            .setTitle("Agregar Nota")
            .setMessage("Escriba la nota..")
            .setView(editText)
            .setPositiveButton("OK") { dialog, which ->
                if(!editText.text.isNullOrEmpty()){
                    viewModel.notasList.add(editText.text.toString())
                    Toast.makeText(requireContext(), "Nota Agregada",
                        Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }

            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

}