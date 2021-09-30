package com.gsoft.debe.ui.pickImagenes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gsoft.debe.R
import com.gsoft.debe.databinding.FragmentNewBinding
import com.gsoft.debe.databinding.FragmentPickImagenesBinding
import com.gsoft.debe.ui.mainViewModel.MainViewModel
import com.gsoft.debe.ui.newScreen.listAdapter
import com.gsoft.debe.ui.newScreen.newViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class pickImagenes : Fragment(R.layout.fragment_pick_imagenes) {

    private lateinit var binding : FragmentPickImagenesBinding
    private val viewModel : MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPickImagenesBinding.bind(view)
        binding.rvSeleccionarImagenes.adapter = PickFotosAdapter(whichCategorytoLoad(), requireContext())
        binding.btnSelectImagenes.setOnClickListener {
            seleccionarImagenes()
        }
        binding.btnAddImagenes.setOnClickListener {
            viewModel.categoriaSeleccionada = ""
            view.findNavController().popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
            if (resultCode === Activity.RESULT_OK) {

                if (data?.clipData  != null) {
                    val count: Int = data?.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        whichCategorytoLoad().add(imageUri)
                    }
                    binding.rvSeleccionarImagenes.adapter?.notifyDataSetChanged()
                }
            }
            if (data?.data != null) {
                val imageUri = data.data
                if (imageUri != null) {
                    whichCategorytoLoad().add(imageUri)
                }
            }
        }
        binding.rvSeleccionarImagenes.adapter?.notifyDataSetChanged()
    }

    private fun seleccionarImagenes() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Pictures: "), 1)
    }

    fun whichCategorytoLoad() : MutableList<Uri>{
        if (viewModel.categoriaSeleccionada == "estudios"){
            return viewModel.listaUrisEstudios
        }else{
            return viewModel.listaUrisLaboratorio
        }
    }
}