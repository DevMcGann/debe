package com.gsoft.debe.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.gsoft.debe.data.models.Paciente
import com.gsoft.debe.utils.Resultado
import kotlinx.coroutines.tasks.await

class PacienteRepositoryImpl (
    private val firestore: FirebaseFirestore
        ) : PacienteRepository {

    override fun insert(paciente: Paciente) {
        val db = firestore
        val pacienteFirestore : MutableMap<String, Any> = mutableMapOf()
        val pacienteID = paciente.dni.toString()
        pacienteFirestore["id"] = pacienteID
        pacienteFirestore["dni"] = pacienteID
        pacienteFirestore["nombre"] = paciente.nombre
        pacienteFirestore["items"] = paciente.items.toString()

        db.collection("pacientes").document(pacienteID).set(pacienteFirestore)
            .addOnSuccessListener {
                Log.d("REPOCREADO", " REPO Paciente Creado ${paciente.nombre} ${paciente.items}")
            }
            .addOnFailureListener {
                Log.d("REPOCREADO", "ERROR creando paciente!")
            }
    }

    override fun update(paciente: Paciente) {
        TODO("Not yet implemented")
    }

    override fun delete(paciente: Paciente) {
        val db = firestore
        db.collection("pacientes").document(paciente.id)
            .delete()
            .addOnSuccessListener {
                Log.d("REPOELIMINAR", "Paciente rajado")
            }
            .addOnFailureListener{
                Log.d("REPOELIMINAR", "Error al eliminar paciente")
            }
    }


    override suspend fun getPaciente(dni: String): Resultado<Paciente?> {
        var founded  = Paciente("1" ,dni="1", "asd", items = null)
        val querySnapshot = firestore.collection("pacientes").get().await()
        for (paciente in querySnapshot.documents){
            paciente.toObject(Paciente::class.java)?.let{
                if (it.dni == dni){
                   founded = it
                }
            }
        }
       return Resultado.Success(founded)
    }
}