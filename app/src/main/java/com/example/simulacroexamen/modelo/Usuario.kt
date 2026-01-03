package com.example.simulacroexamen.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario (
    @SerialName("id")
    val id: String = "",
    @SerialName("nombre")
    val nombre: String = "",
    @SerialName("telefono")
    val telefono: String=""
)