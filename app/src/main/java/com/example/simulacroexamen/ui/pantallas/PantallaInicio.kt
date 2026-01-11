package com.example.simulacroexamen.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.simulacroexamen.R
import com.example.simulacroexamen.modelo.Usuario
import com.example.simulacroexamen.ui.UsuarioUIState

@Composable
fun PantallaInicio(
    appUIState: UsuarioUIState,
    onUsuariosObtenidos: () -> Unit,
    onUsuarioPulsado: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    when (appUIState) {

        UsuarioUIState.Cargando ->
            PantallaCargando(modifier = modifier.fillMaxSize())

        UsuarioUIState.Error ->
            PantallaError(modifier = modifier.fillMaxWidth())

        is UsuarioUIState.ObtenerExito ->
            PantallaListaUsuarios(
                lista = appUIState.usuarios,
                onUsuarioPulsado = onUsuarioPulsado,
                modifier = modifier.fillMaxWidth()
            )

        is UsuarioUIState.CrearExito -> {
            onUsuariosObtenidos()
        }

        is UsuarioUIState.ActualizarExito -> {
            onUsuariosObtenidos()
        }

        is UsuarioUIState.EliminarExito -> {
            onUsuariosObtenidos()
        }
    }
}

@Composable
fun PantallaError(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.error),
        contentDescription = ""
    )
}

@Composable
fun PantallaCargando(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.cargando),
        contentDescription = ""
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaListaUsuarios(
    lista: List<Usuario>,
    onUsuarioPulsado: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(lista) { usuario ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .combinedClickable(
                        onClick = { onUsuarioPulsado(usuario) }
                    )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = usuario.nombre)
                    Text(text = usuario.telefono)
                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}
