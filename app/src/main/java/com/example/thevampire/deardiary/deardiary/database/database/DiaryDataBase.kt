package com.example.thevampire.deardiary.deardiary.database

import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.thevampire.deardiary.deardiary.database.Dao.DiaryDao
import com.example.thevampire.deardiary.deardiary.database.entity.DiaryItem

@Database(entities = arrayOf(DiaryItem::class), version = 3)
abstract class DiaryDataBase : RoomDatabase()
{

    abstract fun getDao() : DiaryDao





    companion object {

        val migration_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE dairy ADD COLUMN upload_status INTEGER DEFAULT 0 NOT NULL")
            }

        }
        val migration_2_3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE dairy ADD COLUMN author TEXT")
            }
        }

     var INSTANCE: DiaryDataBase? = null

        fun getInstance(context: Context): DiaryDataBase {
            if (INSTANCE == null) {
                    synchronized(DiaryDataBase::class){
                        if(INSTANCE ==  null)
                        {
                            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DiaryDataBase::class.java, "my-data.db")
                                    .addMigrations(migration_1_2, migration_2_3)
                                    .build()
                        }
                    }


            }
            return INSTANCE as DiaryDataBase

        }
    }
}
