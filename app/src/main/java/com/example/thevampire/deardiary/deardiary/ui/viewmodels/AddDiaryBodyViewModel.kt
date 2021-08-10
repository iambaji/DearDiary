package com.example.thevampire.deardiary.deardiary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thevampire.deardiary.deardiary.database.entity.DiaryItem
import com.example.thevampire.deardiary.deardiary.repositories.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddDiaryBodyViewModel @Inject constructor(private val repository: DiaryRepository) : ViewModel() {


    fun addNote(title : String, body : String) = viewModelScope.launch { repository.addNote(title,body) }

    fun getDiaryBody(did : Int) = runBlocking { repository.getNote(did) }

    fun updateNote(diaryItem: DiaryItem) = viewModelScope.launch { repository.updateNote(diaryItem) }
}