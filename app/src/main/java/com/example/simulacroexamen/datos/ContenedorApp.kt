package com.example.simulacroexamen.datos

import com.example.simulacroexamen.conexion.SimulacroServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface ContenedorApp {
    val usuarioRepositorio: UsuarioRepositorio
}

class UsuarioContenedorApp : ContenedorApp {

    private val baseUrl = "http://192.168.0.28:3000/"


    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: SimulacroServicioApi by lazy {
        retrofit.create(SimulacroServicioApi::class.java)
    }

    override val usuarioRepositorio: UsuarioRepositorio by lazy {
        ConexionUsuarioRepositorio(servicioRetrofit)
    }

}