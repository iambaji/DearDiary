package com.example.thevampire.deardiary.deardiary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thevampire.deardiary.deardiary.repositories.DiaryAccount
import com.example.thevampire.deardiary.deardiary.repositories.DiaryRepository
import com.example.thevampire.deardiary.deardiary.utils.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val repository: DiaryRepository) : ViewModel() {

    private val _uiState : MutableStateFlow<UIState> = MutableStateFlow(
        UIState.Error("")
    )
    val uiState : StateFlow<UIState> = _uiState

    fun createUser(diaryAccount: DiaryAccount) = viewModelScope.launch {
        if(!performChecks(diaryAccount.name, diaryAccount.email, diaryAccount.password))
            return@launch
        else{
            _uiState.value = UIState.Loading("Please wait")
            repository.createUser(diaryAccount)?.let {
                _uiState.value = UIState.AccountCreated
            }
        }
    }

    private fun performChecks(name : String, username : String, password : String) : Boolean{
        if(name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && password.length>=8 && username.isValidEmail())
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
            if(name.isEmpty())
                errorMessage.append("Enter Name")
            if(password.length<8)
                errorMessage.append("Min 8 Characters")

            _uiState.value = UIState.Error(errorMessage.toString())
            return false
        }
    }



}
sealed class UIState(){
   object AccountCreated  : UIState()
    data class Error(val message : String) : UIState()
    data class Loading(val message: String) : UIState()
}

