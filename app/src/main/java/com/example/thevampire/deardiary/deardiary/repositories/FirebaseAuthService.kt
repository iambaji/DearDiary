package com.example.thevampire.deardiary.deardiary.repositories

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.thevampire.deardiary.deardiary.ui.FeedActivity
import com.example.thevampire.deardiary.deardiary.ui.MainActivity
import com.example.thevampire.deardiary.deardiary.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor() : AuthService{
    private var firebaseAuth = FirebaseAuth.getInstance()
    override suspend fun signInWithEmailAndPassword(email: String, password: String): String? {

        val authResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()
        authResult?.let {
            return it.user.email
        }
        return null
        }


    override suspend fun logOut(): Boolean {
       firebaseAuth.signOut()
        return true
    }

    override suspend fun registerAccount(diaryAccount: DiaryAccount): Boolean {
        val createResult =
            firebaseAuth.createUserWithEmailAndPassword(diaryAccount.email, diaryAccount.password)
                .await()
        return createResult != null
    }


    override suspend fun isEmailVerified(email: String): Boolean {
      return firebaseAuth?.currentUser?.isEmailVerified ?: false
    }

    override suspend fun forgotUser(email: String): Boolean {
        val result = firebaseAuth.sendPasswordResetEmail(email).await()
        return result != null
    }

    override suspend fun getUsername(): String {
        return firebaseAuth.currentUser?.displayName.toString()
    }

    override suspend fun getEmail() = firebaseAuth.currentUser?.email.toString()



}