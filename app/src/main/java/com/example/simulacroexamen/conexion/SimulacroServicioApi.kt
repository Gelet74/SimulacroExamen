package com.example.simulacroexamen.conexion


import com.example.simulacroexamen.modelo.Usuario
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SimulacroServicioApi {

    @GET ("usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>

    @POST("usuarios")
    suspend fun insertarUsuario (
        @Body usuario : Usuario
    ): Usuario

    @PUT("usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id:String,
        @Body usuario:Usuario
    ): Usuario

    @DELETE ("usuarios/{id}")
    suspend fun eliminarUsuario(
        @Path("id") id: String
    ): Usuario
}