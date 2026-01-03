package com.example.simulacroexamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.simulacroexamen.ui.SimulacroApp
import com.example.simulacroexamen.ui.theme.SimulacroExamenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimulacroExamenTheme {
                SimulacroApp()

                }
            }
        }
    }

