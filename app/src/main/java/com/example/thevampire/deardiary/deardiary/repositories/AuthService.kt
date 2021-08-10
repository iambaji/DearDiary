package com.example.thevampire.deardiary.deardiary.repositories

interface AuthService {

    suspend fun signInWithEmailAndPassword(username : String, password : String) : String?

    suspend fun logOut() : Boolean

    suspend fun registerAccount(diaryAccount: DiaryAccount) : Boolean

    suspend fun isEmailVerified(email : String) : Boolean

    suspend fun forgotUser(email : String) : Boolean

    suspend fun getUsername() : String



    suspend fun getEmail() : String
}

