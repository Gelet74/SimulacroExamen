package com.example.simulacroexamen.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simulacroexamen.SimulacroAplicacion
import com.example.simulacroexamen.datos.UsuarioRepositorio
import com.example.simulacroexamen.modelo.Mensaje
import com.example.simulacroexamen.modelo.Usuario
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UsuarioUIState {
    data class ObtenerExito(val usuarios: List<Usuario>) : UsuarioUIState
    data class CrearExito(val usuario: Usuario) : UsuarioUIState
    data class ActualizarExito(val usuario: Usuario) : UsuarioUIState
    data class EliminarExito(val id: String) : UsuarioUIState

    object Error : UsuarioUIState
    object Cargando : UsuarioUIState
}

class UsuarioViewModel(
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel() {

    var usuarioUIState: UsuarioUIState by mutableStateOf(UsuarioUIState.Cargando)
        private set

    var usuarioPulsado: Usuario by mutableStateOf(
        Usuario(id = "", nombre = "", telefono = "")
    )
        private set

    init {
        obtenerUsuarios()
    }

    fun actualizarUsuarioPulsado(usuario: Usuario) {
        usuarioPulsado = usuario
    }

    fun obtenerUsuarios() {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val listaUsuarios = usuarioRepositorio.obtenerUsuarios()
                UsuarioUIState.ObtenerExito(listaUsuarios)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun insertarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val usuarioInsertado = usuarioRepositorio.insertarUsuario(usuario)
                obtenerUsuarios() // refrescar lista
                UsuarioUIState.CrearExito(usuarioInsertado)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun actualizarUsuario(id: String, usuario: Usuario) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                val usuarioActualizado = usuarioRepositorio.actualizarUsuario(id, usuario)
                obtenerUsuarios() // refrescar lista
                UsuarioUIState.ActualizarExito(usuarioActualizado)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    fun enviarMensaje(texto: String) {
        viewModelScope.launch {
            try {
                val nuevoMensaje = usuarioPulsado.mensajes + Mensaje(
                    enviado = true,
                    mensaje = texto
                )

                val usuarioActualizado = usuarioPulsado.copy(
                    mensajes = nuevoMensaje
                )

                val resultado = usuarioRepositorio.actualizarUsuario(
                    usuarioActualizado.id,
                    usuarioActualizado
                )

                usuarioPulsado = resultado
                usuarioUIState = UsuarioUIState.ActualizarExito(resultado)

            } catch (e: Exception) {
                usuarioUIState = UsuarioUIState.Error
            }
        }
    }

    fun eliminarUsuario(id: String) {
        viewModelScope.launch {
            usuarioUIState = UsuarioUIState.Cargando
            usuarioUIState = try {
                usuarioRepositorio.eliminarUsuario(id)
                obtenerUsuarios() // refrescar lista
                UsuarioUIState.EliminarExito(id)
            } catch (e: IOException) {
                UsuarioUIState.Error
            } catch (e: HttpException) {
                UsuarioUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as SimulacroAplicacion)
                val usuarioRepositorio = aplicacion.contenedor.usuarioRepositorio
                UsuarioViewModel(usuarioRepositorio = usuarioRepositorio)
            }
        }
    }
}
