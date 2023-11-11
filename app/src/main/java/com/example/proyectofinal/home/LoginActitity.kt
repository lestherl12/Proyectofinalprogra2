package com.example.proyectofinal.home
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.proyectofinal.HomeActivity
import com.example.proyectofinal.R

class LoginActitity : ComponentActivity() {
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen() {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }
        var isLoginSelected by remember { mutableStateOf(true) }
        var isError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {

                        }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    visualTransformation = if (isPasswordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    trailingIcon = {
                        val icon = if (isPasswordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "inicicar sesion o crear cuenta",
                        fontSize = 16.sp,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            // Acción para crear cuenta
                            isLoginSelected = !isLoginSelected
                        }
                    )

                    Button(
                        onClick = {
                            if (isLoginSelected) {
                                // Iniciar sesión
                                viewModel.signInWithEmailAndPassword(email, password, {
                                    // Acción para navegar a la pantalla principal o realizar alguna acción después del inicio de sesión exitoso
                                    startActivity(Intent(this@LoginActitity, HomeActivity::class.java))
                                    //cierra el loginactivity cuando se inicia sesion Correctamente
                                    finish()
                                }, { errorMessage ->
                                    // Manejar el error al iniciar sesión, por ejemplo, mostrar un mensaje de error

                                    Toast.makeText(this@LoginActitity, errorMessage, Toast.LENGTH_SHORT).show()


                                })



                            } else {
                                // Crear cuenta
                                viewModel.createUserWithEmailAndPassword(email, password,
                                    success = {
                                        // Acción para navegar a la pantalla principal o realizar alguna acción después de la creación exitosa de la cuenta
                                        startActivity(Intent(this@LoginActitity, HomeActivity::class.java))
                                        //cierra el loginactivity
                                        finish()

                                    },
                                    showError = { errorMessage ->
                                        // Manejar el error al crear la cuenta, por ejemplo, mostrar un mensaje de error

                                        Toast.makeText(this@LoginActitity, errorMessage, Toast.LENGTH_SHORT).show()
                                        Log.d("proyectofinal", "Error al crear cuenta: $errorMessage")
                                    }
                                )

                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(if (isLoginSelected) "Iniciar sesión" else "Crear cuenta")
                    }
                }
            }
        }
    }
}
