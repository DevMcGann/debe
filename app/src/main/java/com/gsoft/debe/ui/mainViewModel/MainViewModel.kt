package com.gsoft.debe.ui.mainViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.data.repository.PacienteRepository
import com.gsoft.debe.utils.Event
import com.gsoft.debe.utils.Resultado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.internal.notifyAll
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo : PacienteRepository
): ViewModel() {
    var nombre : String = ""
    var dni : String = ""
    var cirugia : String = ""
    var listaUrisEstudios : MutableList<Uri> = mutableListOf()
    var listaUrisLaboratorio : MutableList<Uri> = mutableListOf()
    var estudiosListURL: MutableList<String> = mutableListOf()
    var laboratorioListURL: MutableList<String> = mutableListOf()
    var notasList : MutableList<String> = mutableListOf()
    var categoriaSeleccionada = ""   //estudios o laboratorio


    var estudiosUploadReady = false
    var laboratorioUploadReady = false

    private var _canCreatePatient: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val canCreateNewPatient: LiveData<Event<Boolean>> = _canCreatePatient

    fun checkCanUploadPatient(){
        if(estudiosUploadReady && laboratorioUploadReady){
            _canCreatePatient.postValue(Event(true))
        }
    }


    fun uploadEstudiosToStorageAndGetURL() = liveData(Dispatchers.IO){
        emit(Resultado.Loading())
        try{
            emit(repo.uploadImagesAndGetURL(listaUrisEstudios!!))
        }catch (e:Exception){
            emit(Resultado.Failure(e))
        }
    }

    fun setImagenesEstudiosURL(urlList:MutableList<String>){
        estudiosListURL = urlList
    }
    fun getImagenesEstudios() : MutableList<String>{
        return estudiosListURL
    }

    fun uploadLaboratorioToStorageAndGetURL() = liveData(Dispatchers.IO){
        emit(Resultado.Loading())
        try{
            emit(repo.uploadImagesLabAndGetURL(listaUrisLaboratorio))
        }catch (e:Exception){
            emit(Resultado.Failure(e))
        }
    }

    fun setImagenesLaboratorioURL(urlList:MutableList<String>){
        laboratorioListURL = urlList
    }
    fun getImagenesLaboratorio() : MutableList<String>{
        return laboratorioListURL
    }

    fun removerNota(nota:String){
        notasList.remove(nota)
    }

    fun agregarPaciente() {
        val paciente = Paciente(
            id = dni,
            dni = dni,
            nombre = nombre,
            cirugia = cirugia,
           estudios = estudiosListURL,
            laboratorio = laboratorioListURL,
            notas = notasList
        )
        Log.d("AGREGAR", "agregar paciente llamado")
        repo.insert(paciente)
    }



}