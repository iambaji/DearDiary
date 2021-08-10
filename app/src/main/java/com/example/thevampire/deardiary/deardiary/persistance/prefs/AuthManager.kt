package com.example.thevampire.deardiary.deardiary.persistance.prefs



interface AuthManager {

    suspend fun saveUserEmail(email : String)

    suspend fun logoutUser()

    suspend fun getLoggedInUserEmail() : String?

    suspend fun isUserLoggedIn() : Boolean

}