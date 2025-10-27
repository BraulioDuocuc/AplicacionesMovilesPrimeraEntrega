// viewmodel/AuthViewModel.kt
package com.appgamerzone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgamerzone.data.model.User
import com.example.appgamerzone.data.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class AuthState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isRegistrationSuccessful: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val loginError: String? = null,
    val isLoading: Boolean = false
)

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableLiveData(AuthState())
    val uiState: LiveData<AuthState> = _uiState

    fun updateFullName(fullName: String) {
        _uiState.value = _uiState.value?.copy(fullName = fullName, fullNameError = null)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value?.copy(email = email, emailError = null, loginError = null)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value?.copy(password = password, passwordError = null, loginError = null)
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
        if (currentState.fullName.isBlank()) {
            _uiState.value = currentState.copy(fullNameError = "El nombre completo es requerido")
            isValid = false
        }
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(emailError = "El email es requerido")
            isValid = false
        } else if (!isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(emailError = "Ingresa un email válido")
            isValid = false
        }
        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(passwordError = "La contraseña es requerida")
            isValid = false
        } else if (currentState.password.length < 6) {
            _uiState.value = currentState.copy(passwordError = "La contraseña debe tener al menos 6 caracteres")
            isValid = false
        }
        if (currentState.confirmPassword != currentState.password) {
            _uiState.value = currentState.copy(confirmPasswordError = "Las contraseñas no coinciden")
            isValid = false
        }
        return isValid
    }

    fun validateLogin(): Boolean {
        val currentState = _uiState.value ?: return false
        var isValid = true
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(emailError = "El email es requerido")
            isValid = false
        } else if (!isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(emailError = "Ingresa un email válido")
            isValid = false
        }
        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(passwordError = "La contraseña es requerida")
            isValid = false
        }
        return isValid
    }

    fun registerUser(age: Int = 18, isDuocStudent: Boolean = false, referralCode: String? = null) {
        val current = _uiState.value ?: return
        if (!validateForm()) return
        viewModelScope.launch {
            _uiState.value = current.copy(isLoading = true)
            val user = User(
                email = current.email,
                password = current.password,
                fullName = current.fullName,
                age = age,
                isDuocStudent = isDuocStudent,
                referralCode = referralCode
            )
            val startTime = System.currentTimeMillis()
            val result = repository.register(user)
            val elapsed = System.currentTimeMillis() - startTime
            if (elapsed < 2000) {
                delay(2000 - elapsed)
            }
            result.onSuccess {
                _uiState.value = current.copy(isRegistrationSuccessful = true, isLoading = false)
            }.onFailure {
                _uiState.value = current.copy(emailError = it.message, isLoading = false)
            }
        }
    }

    fun loginUser() {
        val current = _uiState.value ?: return
        if (!validateLogin()) return
        viewModelScope.launch {
            _uiState.value = current.copy(isLoading = true, loginError = null)
            val startTime = System.currentTimeMillis()
            val result = repository.login(current.email, current.password)
            val elapsed = System.currentTimeMillis() - startTime
            if (elapsed < 2000) {
                delay(2000 - elapsed)
            }
            result.onSuccess {
                _uiState.value = current.copy(isLoginSuccessful = true, isLoading = false)
            }.onFailure {
                _uiState.value = current.copy(loginError = it.message, isLoading = false)
            }
        }
    }

    fun resetRegistrationState() {
        _uiState.value = AuthState()
    }

    fun resetLoginState() {
        val current = _uiState.value ?: AuthState()
        _uiState.value = current.copy(isLoginSuccessful = false, loginError = null, isLoading = false)
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}