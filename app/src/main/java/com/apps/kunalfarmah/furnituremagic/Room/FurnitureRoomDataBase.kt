package com.apps.kunalfarmah.furnituremagic.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Furniture::class], version = 2)
abstract class FurnitureRoomDataBase : RoomDatabase() {
    abstract fun FurnitureDao(): FurnitureDao?

    companion object {
        private var INSTANCE: FurnitureRoomDataBase? = null

        @Synchronized
        fun getInstance(mContext: Context?): FurnitureRoomDataBase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        mContext!!.applicationContext,
                        FurnitureRoomDataBase::class.java, "FurnitureRoomDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }
    }
}