package com.example.thevampire.deardiary.deardiary.repositories

import com.example.thevampire.deardiary.deardiary.auth.AuthService
import com.example.thevampire.deardiary.deardiary.persistance.database.Dao.DiaryDao
import com.example.thevampire.deardiary.deardiary.persistance.database.entity.DiaryItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DiaryRepository @Inject constructor(private val authService: AuthService, private val diaryDao : DiaryDao) {


    suspend fun loginUser(email : String, password : String) : String?{
        return authService.signInWithEmailAndPassword(email,password)
    }

    suspend fun forgotUser(email: String) : Boolean{
        return authService.forgotUser(email)
    }

    suspend fun logOutUser() = authService.logOut()

    suspend fun getUsername() = authService.getUsername()

    suspend fun getNotes() = withContext(Dispatchers.IO){
        val email = authService.getEmail()
        return@withContext diaryDao.getAll(email)
    }

    suspend fun getNotesFromServer() : List<DiaryItem> {
        return withContext(Dispatchers.IO){
            val diaryItems = arrayListOf<DiaryItem>()
            val firestore = FirebaseFirestore.getInstance()
            val email = authService.getEmail()
            val posts = firestore?.collection("users/$email/posts").get().await()
            posts.forEach {
                if(it.exists()){
                    val item = it.toObject(DiaryItem::class.java)
                    item.upload_status = 1
                    diaryItems.add(item)
                }
            }
            return@withContext diaryItems
        }
    }

    suspend fun getNote(did : Int) : DiaryItem
    {
      return withContext(Dispatchers.IO){
          return@withContext diaryDao.getBody(did)
      }
    }

    suspend fun addNote(title : String, body : String){
        val now = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd-MM-yyyy, HH : mm a").format(now.time)
        val dairy  = DiaryItem(null,sdf.toUpperCase(),title.toString(),body.toString(),0, authService.getEmail())
        diaryDao.add( dairy )
    }

    suspend fun updateNote(diaryItem: DiaryItem) = diaryDao.updateBody(diaryItem)


    suspend fun createUser(diaryAccount: DiaryAccount) : Boolean{
        return authService.createUser(diaryAccount)
    }
}