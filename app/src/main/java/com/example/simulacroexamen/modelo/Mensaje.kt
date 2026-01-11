package com.example.simulacroexamen.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mensaje(
    @SerialName("enviado")
    val enviado: Boolean,

    @SerialName("mensaje")
    val mensaje: String
)
