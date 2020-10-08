package com.apps.kunalfarmah.furnituremagic.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Furniture.class}, version = 1)
public abstract class FurnitureRoomDataBase extends RoomDatabase {

    private static FurnitureRoomDataBase INSTANCE;

    public abstract FurnitureDao FurnitureDao();

    public static synchronized FurnitureRoomDataBase getInstance(Context mContext) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(),
                    FurnitureRoomDataBase.class, "FurnitureRoomDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
