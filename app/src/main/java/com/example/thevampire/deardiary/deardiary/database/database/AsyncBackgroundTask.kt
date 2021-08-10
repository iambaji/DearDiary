package com.example.thevampire.deardiary.deardiary.database.database

import android.os.AsyncTask
import com.example.thevampire.deardiary.deardiary.database.DiaryDataBase
import com.example.thevampire.deardiary.deardiary.database.entity.DiaryItem

class AsyncBackgroundTask(val mdb : DiaryDataBase?) : AsyncTask<DiaryItem, Void, Void>() {
    override fun doInBackground(vararg params: DiaryItem) : Void? {
//        mdb?.getDao()?.add(params[0])
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
    }
}