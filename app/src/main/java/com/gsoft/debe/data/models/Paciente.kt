package com.gsoft.debe.data.models

data class Paciente (
    val id : String,
    val dni : String,
    val nombre: String,
    val cirugia: String,
    val estudios: MutableList<String>? = null,
   // val laboratorio: MutableList<String>? = null,
    val notas: MutableList<String>? = null
        )
{
    constructor() : this("", "","",
        "", estudios = null, notas = null)
}