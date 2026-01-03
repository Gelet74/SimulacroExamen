package com.example.simulacroexamen.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.simulacroexamen.R
import com.example.simulacroexamen.ui.pantallas.PantallaInicio


enum class Pantallas(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.pantalla_inicio),
    Insertar(titulo = R.string.insertar),
    Actualizar(titulo = R.string.actualizar)
}

@Composable
fun SimulacroApp(
    viewModel: UsuarioViewModel = viewModel(factory = UsuarioViewModel.Factory),
    navController: NavHostController = rememberNavController()
){
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual =Pantallas.valueOf(
        pilaRetroceso?.destination?.route?: Pantallas.Inicio.name
    )
    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = {navController.navigateUp()}
            )
        },
        floatingActionButton = {
            if(pantallaActual.titulo == R.string.pantalla_inicio) {
                FloatingActionButton(
                    onClick = { navController.navigate(route = com.example.simulacroexamen.ui.Pantallas.Insertar.name) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.insertar_usuario)
                    )
                }
            }
        }
    ) { innerPadding ->
        val uiState = viewModel.usuarioUIState

        NavHost(
            navController = navController,
            startDestination = Pantallas.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ){
            // Grafo de las rutas
            composable(route = Pantallas.Inicio.name) {
                PantallaInicio(
                    appUIState = uiState,
                    onUsuariosObtenidos = {viewModel.obtenerUsuarios()},
                    onUsuarioPulsado = {
                        viewModel.actualizarUsuarioPulsado(it)
                        navController.navigate(Pantallas.Actualizar.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
                composable(route = Pantallas.Actualizar.name) {
                    PantallaConversacion(
                        usuario = viewModel.usuarioPulsado,
                        onTrabajadorActualizado = {
                            viewModel.actualizarUsuario(it.id, it)
                            navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: com.example.simulacroexamen.ui.Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if(puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
        modifier = modifier
    )
}