package com.example.thevampire.deardiary.deardiary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thevampire.deardiary.deardiary.persistance.database.entity.DiaryItem
import com.example.thevampire.deardiary.deardiary.persistance.prefs.AuthSession
import com.example.thevampire.deardiary.deardiary.repositories.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: DiaryRepository) : ViewModel(){

    @Inject lateinit var authSession: AuthSession

    private val _diaryNotes = MutableLiveData<List<DiaryItem>>()
    val diaryNotes : LiveData<List<DiaryItem>>  = _diaryNotes
    val uiState = MutableStateFlow<FeedUIState>(FeedUIState.NotesSuccess(emptyList()))


    fun logoutUser()  {
        viewModelScope.launch {
            repository.logOutUser()
            authSession.logoutUser()}
    }

    fun getUsername() = runBlocking { return@runBlocking repository.getUsername() }



    fun getNotes(){
        viewModelScope.launch {
            uiState.value = FeedUIState.Loading("Please wait!")
            val notes = repository.getNotes()
//            _diaryNotes.value = notes
            uiState.value = FeedUIState.NotesSuccess(notes)
        }
    }

    fun downloadFromServer() = runBlocking { repository.getNotesFromServer() }


    sealed class FeedUIState{
        data class NotesSuccess(val notes : List<DiaryItem>) : FeedUIState()
        data class Error(val message : String) : FeedUIState()
        data class Loading(val message: String) : FeedUIState()
    }
}