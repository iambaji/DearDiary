package com.example.thevampire.deardiary.deardiary.persistance.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val STORE_NAME = "auth_store"
val Context.dataStore  by preferencesDataStore(name = STORE_NAME)

@Singleton
class AuthSession @Inject constructor(@ApplicationContext val context: Context) : AuthManager{
    override suspend fun saveUserEmail(email: String) {
        context.dataStore.edit {
        it[PreferenceKeys.USER_EMAIL_ID] = email
        }
    }

    override suspend fun logoutUser() {
       context.dataStore.edit {
           it[PreferenceKeys.USER_EMAIL_ID] = ""
       }
    }



    override suspend fun getLoggedInUserEmail(): String? {
       return context.dataStore.data.first()[PreferenceKeys.USER_EMAIL_ID] ?: null
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return getLoggedInUserEmail() != null
    }

}

private object PreferenceKeys{
    val USER_EMAIL_ID = stringPreferencesKey("user_email_id")
}