package com.gsoft.debe.ui.searchScreen

import androidx.lifecycle.ViewModel
import com.gsoft.debe.data.repository.PacienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class searchViewModel @Inject constructor(
    private val repo : PacienteRepository
) : ViewModel() {

}