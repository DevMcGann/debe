package com.gsoft.debe.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.utils.Resultado
import kotlinx.coroutines.tasks.await
import java.util.*

class PacienteRepositoryImpl (
    private val firestore: FirebaseFirestore
        ) : PacienteRepository {

    override fun insert(paciente: Paciente) {
        val db = firestore
        val pacienteFirestore : MutableMap<String, Any> = mutableMapOf()
        val pacienteID = paciente.dni
        pacienteFirestore["id"] = pacienteID
        pacienteFirestore["dni"] = pacienteID
        pacienteFirestore["nombre"] = paciente.nombre
        pacienteFirestore["cirugia"] = paciente.cirugia
        pacienteFirestore["estudios"] = paciente.estudios as List<String>
        pacienteFirestore["laboratorio"] = paciente.laboratorio as List<String>
        pacienteFirestore["notas"] = paciente.notas as List<String>
        Log.d("REPOCREADO", " paciente ${paciente.toString()} ")

        db.collection("pacientes").document(pacienteID).set(pacienteFirestore)
            .addOnSuccessListener {
                Log.d("REPOCREADO", " REPO Paciente Creado ${paciente.nombre} ")
            }
            .addOnFailureListener {
                Log.d("REPOCREADO", "ERROR creando paciente!")
            }
    }

    override fun update(paciente: Paciente) {
        delete(paciente)
        insert(paciente)
    }

    override fun delete(paciente: Paciente) {
        val db = firestore
        db.collection("pacientes").document(paciente.id!!)
            .delete()
            .addOnSuccessListener {
                Log.d("REPOELIMINAR", "Paciente rajado")
            }
            .addOnFailureListener{
                Log.d("REPOELIMINAR", "Error al eliminar paciente")
            }
    }


    override suspend fun getPaciente(dni: String): Resultado<Paciente?> {
        val querySnapshot = firestore.collection("pacientes").document(dni).get().await()
        val documento = querySnapshot.toObject<Paciente>(Paciente::class.java)

       return Resultado.Success(documento)
    }

    override suspend fun uploadImagesAndGetURL(imgList: MutableList<Uri>): Resultado<MutableList<String>> {
        var imgListUrl : MutableList<String> = mutableListOf()
        for (imagen in imgList) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/imagenes/$filename")
            ref.putFile(imagen).await()
            ref.downloadUrl.addOnCompleteListener {
                imgListUrl.add(it.result.toString())
                Log.d("DataSource->", "imgURL = $imgListUrl")
            }
        }
        return Resultado.Success(imgListUrl)
    }

    override suspend fun uploadImagesLabAndGetURL(imgList: MutableList<Uri>): Resultado<MutableList<String>> {
        var imgListUrl : MutableList<String> = mutableListOf()
        for (imagen in imgList) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/imagenes/$filename")
            ref.putFile(imagen).await()
            ref.downloadUrl.addOnCompleteListener {
                imgListUrl.add(it.result.toString())
                Log.d("DataSource->", "imgURL = $imgListUrl")
            }
        }
        return Resultado.Success(imgListUrl)
    }


}