package com.gsoft.debe.utils

import java.lang.Exception

sealed class Resultado<out T> {

    class Loading<out T> : Resultado<T>()

    data class Success<out T>(val data: T): Resultado<T>()

    data class Failure(val exception: Exception) : Resultado<Nothing>()
}