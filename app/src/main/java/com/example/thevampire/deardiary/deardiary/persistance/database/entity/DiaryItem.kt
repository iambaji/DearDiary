package com.example.thevampire.deardiary.deardiary.persistance.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


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