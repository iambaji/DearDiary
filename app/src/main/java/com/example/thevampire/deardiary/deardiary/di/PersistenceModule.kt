package com.example.thevampire.deardiary.deardiary.di

import android.app.Application
import com.example.thevampire.deardiary.deardiary.database.Dao.DiaryDao
import com.example.thevampire.deardiary.deardiary.database.DiaryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDiaryDatabase(application : Application) : DiaryDataBase{
        return DiaryDataBase.getInstance(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideDiaryDao(appDatabase : DiaryDataBase) : DiaryDao{
       return appDatabase.getDao()
    }
}