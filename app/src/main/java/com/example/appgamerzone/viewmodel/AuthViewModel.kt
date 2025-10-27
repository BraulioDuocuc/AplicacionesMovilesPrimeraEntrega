// viewmodel/AuthViewModel.kt
package com.appgamerzone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class AuthState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isRegistrationSuccessful: Boolean = false
)

class AuthViewModel : ViewModel() {
    private val _uiState = MutableLiveData(AuthState())
    val uiState: LiveData<AuthState> = _uiState

    fun updateFullName(fullName: String) {
        _uiState.value = _uiState.value?.copy(fullName = fullName, fullNameError = null)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value?.copy(email = email, emailError = null)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value?.copy(password = password, passwordError = null)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value?.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = null
        )
    }

    fun validateForm(): Boolean {
        val currentState = _uiState.value ?: return false

        var isValid = true

        // Validar nombre
        if (currentState.fullName.isBlank()) {
            _uiState.value = currentState.copy(
                fullNameError = "El nombre completo es requerido"
            )
            isValid = false
        }

        // Validar email
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(
                emailError = "El email es requerido"
            )
            isValid = false
        } else if (!isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(
                emailError = "Ingresa un email válido"
            )
            isValid = false
        }

        // Validar contraseña
        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(
                passwordError = "La contraseña es requerida"
            )
            isValid = false
        } else if (currentState.password.length < 6) {
            _uiState.value = currentState.copy(
                passwordError = "La contraseña debe tener al menos 6 caracteres"
            )
            isValid = false
        }

        // Validar confirmación de contraseña
        if (currentState.confirmPassword != currentState.password) {
            _uiState.value = currentState.copy(
                confirmPasswordError = "Las contraseñas no coinciden"
            )
            isValid = false
        }

        return isValid
    }

    fun registerUser() {
        // Simular registro exitoso
        _uiState.value = _uiState.value?.copy(isRegistrationSuccessful = true)
    }

    fun resetRegistrationState() {
        _uiState.value = AuthState()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}