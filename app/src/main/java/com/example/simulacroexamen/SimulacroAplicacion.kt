package com.example.simulacroexamen

import android.app.Application
import com.example.simulacroexamen.datos.ContenedorApp
import com.example.simulacroexamen.datos.UsuarioContenedorApp

class SimulacroAplicacion : Application() {
    lateinit var contenedor : ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = UsuarioContenedorApp()
    }
}