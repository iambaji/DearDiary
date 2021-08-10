package com.example.thevampire.deardiary.deardiary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thevampire.deardiary.deardiary.persistance.prefs.AuthSession
import com.example.thevampire.deardiary.deardiary.repositories.DiaryRepository
import com.example.thevampire.deardiary.deardiary.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DiaryRepository) : ViewModel() {

    @Inject lateinit var authSession: AuthSession


    private val _uiState : MutableStateFlow<LoginScreenUIState> = MutableStateFlow(
        LoginScreenUIState.Idle
    )
    val uiState : StateFlow<LoginScreenUIState> = _uiState

    fun isUserLoggedIn() : Boolean =  runBlocking { return@runBlocking authSession.isUserLoggedIn() }

    private fun setUserEmail(email : String) = viewModelScope.launch { authSession.saveUserEmail(email) }

    fun loginUser(email : String, password: String) = viewModelScope.launch {
        if(!performChecks(email, password))
            return@launch
        else{
            try {
                _uiState.value = LoginScreenUIState.Loading("Please wait, Logging In User")
                repository.loginUser(email,password)?.let {
                    _uiState.value = LoginScreenUIState.LoginSuccess(it)
                    setUserEmail(it)
                } ?: run{_uiState.value = LoginScreenUIState.Error("Invalid Logins") }
            }catch (e : Exception){
                e.printStackTrace()
                _uiState.value = LoginScreenUIState.Error(e.message.toString())
            }

        }

    }


    fun forgotPassword(email: String){
        val error = StringBuilder()
        if(email.length<6 || !email.isValidEmail())
            error.append("Enter Valid Email address to Proceed!")
        else{
            _uiState.value = LoginScreenUIState.Loading("Please wait, Sending Reset Request")
            viewModelScope.launch { repository.forgotUser(email)?.let {
                _uiState.value = LoginScreenUIState.ResetAccount("Reset Email Sent!")
            }}
        }

    }

    private fun performChecks(username : String, password : String) : Boolean{
        if(username.isNotEmpty() && password.isNotEmpty() && password.length>=8 && username.isValidEmail())
            return true
        else
        {
            val errorMessage = StringBuilder()
            if(username.isEmpty())
                errorMessage.append("Enter Email")
            if(!username.isValidEmail())
                errorMessage.append("Enter Valid Email")
            if(password.isEmpty())
                errorMessage.append("Enter Password")
            if(password.length<8)
                errorMessage.append("Min 8 Characters")

            _uiState.value = LoginScreenUIState.Error(errorMessage.toString())
            return false
        }
    }



    sealed class LoginScreenUIState{
        data class LoginSuccess(val email : String) : LoginScreenUIState()
        data class Error(val message : String) : LoginScreenUIState()
        data class Loading(val message : String) : LoginScreenUIState()
        object Idle : LoginScreenUIState()
        data class ResetAccount(val message: String) : LoginScreenUIState()
    }
}