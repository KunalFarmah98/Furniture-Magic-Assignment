package com.apps.kunalfarmah.furnituremagic.Repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.apps.kunalfarmah.furnituremagic.Room.Furniture
import com.apps.kunalfarmah.furnituremagic.Room.FurnitureDao
import com.apps.kunalfarmah.furnituremagic.Room.FurnitureRoomDataBase

class FurnitureRepository(application: Application?) {
    val furnitureDao: FurnitureDao
    val furnitures: LiveData<List<Furniture?>?>
    fun insert(item: Furniture?) {
        InsertFurnitureAsyncTask(furnitureDao).execute(item)
    }

    fun delete(item: Furniture?) {
        DeleteFurnitureAsyncTask(furnitureDao).execute(item)
    }

    class InsertFurnitureAsyncTask constructor(private val furnitureDao: FurnitureDao) :
        AsyncTask<Furniture?, Void?, Void?>() {
        override fun doInBackground(vararg posts: Furniture?): Void? {
            furnitureDao.insert(posts[0])
            return null
        }

    }

    class DeleteFurnitureAsyncTask constructor(private val furnitureDao: FurnitureDao) :
        AsyncTask<Furniture?, Void?, Void?>() {
        override fun doInBackground(vararg posts: Furniture?): Void? {
            furnitureDao.delete(posts[0])
            return null
        }

    }


    fun insertFurnitures(postlist: List<Furniture?>?) {
        insertAsyncTask(furnitureDao).execute(postlist)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: FurnitureDao) :
        AsyncTask<List<Furniture?>?, Void?, Void?>() {
        protected override fun doInBackground(vararg params: List<Furniture?>?): Void? {
            mAsyncTaskDao.insertFurnitures(params[0])
            return null
        }

    }

    fun deleteAll() {
        deleteAllAsyncTask(furnitureDao).execute()
    }

    private class deleteAllAsyncTask internal constructor(private val mAsyncTaskDao: FurnitureDao) :
        AsyncTask<List<Furniture?>?, Void?, Void?>() {
        protected override fun doInBackground(vararg params: List<Furniture?>?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }

    }

    init {
        val db = FurnitureRoomDataBase.getInstance(application)
        furnitureDao = db!!.FurnitureDao()!!
        furnitures = furnitureDao.allFurnitures
    }
}