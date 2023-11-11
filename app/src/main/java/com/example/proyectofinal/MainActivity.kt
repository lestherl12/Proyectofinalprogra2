package com.example.proyectofinal
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.proyectofinal.home.LoginActitity


class MainActivity : ComponentActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 3 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SplashScreen()
        }

        // Mostrar la imagen durante unos segundos

        Handler().postDelayed({

            val intent = Intent(this, LoginActitity::class.java)
            startActivity(intent)

            // Cerrar la actividad actual
            finish()
        }, SPLASH_TIME_OUT)
    }

    @Composable
    fun SplashScreen() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Muestra la imagen en la pantalla de inicio
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}
