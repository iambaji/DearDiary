package com.example.thevampire.deardiary.deardiary.auth

import com.example.thevampire.deardiary.deardiary.repositories.DiaryAccount

interface AuthService {

    suspend fun signInWithEmailAndPassword(username : String, password : String) : String?

    suspend fun logOut() : Boolean

    suspend fun registerAccount(diaryAccount: DiaryAccount) : Boolean

    suspend fun isEmailVerified(email : String) : Boolean

    suspend fun forgotUser(email : String) : Boolean

    suspend fun getUsername() : String



    suspend fun getEmail() : String

    suspend fun createUser(diaryAccount: DiaryAccount) : Boolean
}

