package com.example.simulacroexamen.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simulacroexamen.modelo.Mensaje
import com.example.simulacroexamen.modelo.Usuario

@Composable
fun PantallaConversacion(
    usuario: Usuario,
    onEnviarMensaje: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var mensajeNuevo by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        // LISTA DE MENSAJES
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(usuario.mensajes) { mensaje ->
                MensajeItem(mensaje)
            }
        }

        // CAMPO DE TEXTO + BOTÓN ENVIAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = mensajeNuevo,
                onValueChange = { mensajeNuevo = it },
                label = { Text("Escribe un mensaje") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    onEnviarMensaje(mensajeNuevo)
                    mensajeNuevo = ""
                },
                enabled = mensajeNuevo.isNotBlank()
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun MensajeItem(mensaje: Mensaje) {
    val color = if (mensaje.enviado) Color(0xFFD1E8FF) else Color(0xFFE8E8E8)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (mensaje.enviado) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.widthIn(max = 260.dp)
        ) {
            Text(
                text = mensaje.mensaje,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
