package com.example.thevampire.deardiary.deardiary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.*


@Entity(tableName = "diary")
data class DiaryItem(@PrimaryKey(autoGenerate = true) var did: Int?,
                     @ColumnInfo var date: String,
                     @ColumnInfo var title : String,
                     @ColumnInfo var body: String,
                     @ColumnInfo var upload_status: Int,
                     @ColumnInfo var author: String?)
{
    constructor() : this(0,"","","",0,"")
}