package com.example.proyectofinal.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit, showError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Lógica de inicio de sesión con Firebase
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("proyectofinal", "signInWithEmailAndPassword logueado")
                            home()
                        } else {
                            val errorMessage = "Error al iniciar sesión: ${task.exception?.message}"
                            Log.d("proyectofinal", "signInWithEmailAndPassword: $errorMessage")
                            showError(errorMessage)
                        }
                    }
            } catch (ex: Exception) {
                // Manejar errores
                val errorMessage = "Error al iniciar sesión: ${ex.message}"
                Log.d("proyectofinal", "signInWithEmailAndPassword: $errorMessage")
                showError(errorMessage)
            }
        }
    }


    fun createUserWithEmailAndPassword(email: String, password: String, success: () -> Unit, showError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Lógica de creación de usuario con Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("proyectofinal", "createUserWithEmailAndPassword usuario creado")
                            success()
                        } else {
                            val errorMessage = "Error al crear usuario: ${task.exception?.message}"
                            Log.d("proyectofinal", "createUserWithEmailAndPassword: $errorMessage")
                            showError(errorMessage)
                        }
                    }
            } catch (ex: Exception) {
                // Manejar errores
                val errorMessage = "Error al crear usuario: ${ex.message}"
                Log.d("proyectofinal", "createUserWithEmailAndPassword: $errorMessage")
                showError(errorMessage)
            }
        }
    }
}
