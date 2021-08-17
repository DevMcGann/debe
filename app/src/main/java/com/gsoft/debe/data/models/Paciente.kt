package com.gsoft.debe.data.models

data class Paciente (
    val id : String,
    val dni : String,
    val nombre: String,
    val items: List<String>?
        )