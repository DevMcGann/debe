package com.gsoft.debe.data.repository

import android.net.Uri
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.utils.Resource
import com.gsoft.debe.utils.Resultado

interface PacienteRepository {
    fun insert(paciente: Paciente)

    fun update(paciente: Paciente)

    fun delete (paciente: Paciente)

    suspend fun getPaciente(dni:String) : Resultado<Paciente?>

    suspend fun uploadImagesAndGetURL(imgList: MutableList<Uri>) : Resultado<MutableList<String>>

   //suspend fun uploadImagesLabAndGetURL(imgList: MutableList<Uri>) : Resultado<MutableList<String>>


}