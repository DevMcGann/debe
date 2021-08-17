package com.gsoft.debe.ui.newScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.data.repository.PacienteRepository
import com.gsoft.debe.utils.Resultado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class newViewModel @Inject constructor(
    private val repo : PacienteRepository
) : ViewModel() {

    // Observables

    private val _items = MutableLiveData<MutableList<String>>()
    val items: MutableLiveData<MutableList<String>> = _items

    private val _paciente = MutableLiveData<Paciente>()
    val paciente : MutableLiveData<Paciente> = _paciente

    var cargando = MutableLiveData(false)
    var error = MutableLiveData(false)



    // Funciones

    fun crearPaciente(paciente:Paciente){
        repo.insert(paciente)
    }

    fun updatePaciente (paciente: Paciente){
        repo.update(paciente)
    }

    fun deletePaciente(paciente: Paciente){
        repo.delete(paciente)
    }

    fun fetchPaciente (dni:String) = liveData(Dispatchers.IO){
        cargando.value = true
        error.value = false
        try{
            emit(dni?.let { repo.getPaciente(it) })
            cargando.value = false
            error.value = false
        }catch (e:Exception){
            cargando.value = false
            error.value = true
            emit(Resultado.Failure(e))
        }
    }

}