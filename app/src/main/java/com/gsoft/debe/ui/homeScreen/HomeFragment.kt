package com.gsoft.debe.ui.homeScreen


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gsoft.debe.R
import com.gsoft.debe.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        goToNew()
        goToSearch()
    }

    private fun goToNew() {
        binding.btnNew.setOnClickListener {
            Log.d("NAVIGATION", "NAVEGANDO A NEW SCREEN")
            findNavController().navigate(R.id.action_homeFragment_to_newFragment)
        }
    }

    private fun goToSearch(){
        binding.btnSearch.setOnClickListener {
            Log.d("NAVIGATION", "NAVEGANDO A SEARCH SCREEN")
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }




}