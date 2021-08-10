package com.example.thevampire.deardiary.deardiary.auth

import com.example.thevampire.deardiary.deardiary.repositories.DiaryAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class FirebaseAuthService @Inject constructor() : AuthService {
    private var firebaseAuth = FirebaseAuth.getInstance()
    override suspend fun signInWithEmailAndPassword(email: String, password: String): String? {

        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
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

    override suspend fun createUser(diaryAccount: DiaryAccount): Boolean {
        try {
            firebaseAuth.createUserWithEmailAndPassword(diaryAccount.email, diaryAccount.password)
                .await().user?.let {
                val requestProfileUpdate = UserProfileChangeRequest.Builder()
                requestProfileUpdate.setDisplayName(diaryAccount.name)
                it.updateProfile(requestProfileUpdate.build()).await()
                return true
            } ?: return false
        } catch (e: ExecutionException) {
            e.printStackTrace()
            return false
        } catch (e: TimeoutException) {
            e.printStackTrace()
            return false
        }

    }


}