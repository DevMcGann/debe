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
        val pacienteID = paciente.dni
        pacienteFirestore["id"] = pacienteID!!
        pacienteFirestore["dni"] = pacienteID
        pacienteFirestore["nombre"] = paciente.nombre!!
        pacienteFirestore["items"] = paciente.items as List<String>
        Log.d("REPOCREADO", " llega????? ${paciente.nombre} ${paciente.items}")
        db.collection("pacientes").document(pacienteID).set(pacienteFirestore)
            .addOnSuccessListener {
                Log.d("REPOCREADO", " REPO Paciente Creado ${paciente.nombre} ${paciente.items}")
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
        var paciente : Paciente = Paciente(id= "", dni="", nombre = "", items = null)
        var lista : MutableList<String> = mutableListOf()
        val querySnapshot = firestore.collection("pacientes").document(dni).get().await()

        val documento = querySnapshot.toObject<Paciente>(Paciente::class.java)



       return Resultado.Success(documento)
    }
}