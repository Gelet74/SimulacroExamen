package com.example.simulacroexamen.datos

import com.example.simulacroexamen.conexion.SimulacroServicioApi
import com.example.simulacroexamen.modelo.Usuario

interface UsuarioRepositorio {
    suspend fun obtenerUsuarios(): List<Usuario>
    suspend fun insertarUsuario (usuario: Usuario): Usuario
    suspend fun actualizarUsuario (id: String, usuario: Usuario): Usuario
    suspend fun eliminarUsuario (id: String): Usuario
}

class ConexionUsuarioRepositorio (
    private val usuariosServicioApi: SimulacroServicioApi
) : UsuarioRepositorio {
    override suspend fun obtenerUsuarios(): List<Usuario> =usuariosServicioApi.obtenerUsuarios()
    override suspend fun insertarUsuario(usuario: Usuario): Usuario = usuariosServicioApi.insertarUsuario(usuario)
    override suspend fun actualizarUsuario(id: String, usuario: Usuario): Usuario = usuariosServicioApi.actualizarUsuario(id, usuario)
    override suspend fun eliminarUsuario(id: String): Usuario =usuariosServicioApi.eliminarUsuario(id)
    }
