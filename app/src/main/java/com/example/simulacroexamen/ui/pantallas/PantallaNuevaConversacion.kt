package com.example.simulacroexamen.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simulacroexamen.modelo.Usuario
import com.example.simulacroexamen.modelo.Mensaje

@Composable
fun PantallaNuevaConversacion(
    onCrear: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val formularioValido = nombre.isNotBlank() && telefono.isNotBlank() && mensaje.isNotBlank()

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Móvil del usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text("Escribe un mensaje") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Button(
            onClick = {
                val nuevoUsuario = Usuario(
                    id = "",
                    nombre = nombre,
                    telefono = telefono,
                    mensajes = listOf(
                        Mensaje(
                            enviado = true,
                            mensaje = mensaje
                        )
                    )
                )
                onCrear(nuevoUsuario)
            },
            enabled = formularioValido,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}
