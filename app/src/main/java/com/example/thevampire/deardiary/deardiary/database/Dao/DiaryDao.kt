package com.example.thevampire.deardiary.deardiary.database.Dao

import androidx.room.*
import com.example.thevampire.deardiary.deardiary.database.entity.DiaryItem


@Dao
interface DiaryDao{

    @Delete
    suspend fun delete(item : DiaryItem) : Int

    @Query("SELECT * FROM DIARY where author = :email")
    fun getAll(email: String) : List<DiaryItem>

    @Insert
    suspend fun add(diary : DiaryItem)

    @Insert
    suspend fun addAll(vararg item : DiaryItem)


    @Query("select * from DIARY where did = :did")
    fun getBody(did : Int) : DiaryItem


    @Update
    suspend fun updateBody(vararg item : DiaryItem?) : Int

    @Query("DELETE FROM DIARY ")
    suspend fun deleteAll() : Int

    @Update
    suspend fun updateUploadStatus(vararg item : DiaryItem?) : Int



}