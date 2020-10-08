package com.apps.kunalfarmah.furnituremagic.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FurnitureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Furniture item);

    @Query("SELECT * from FURNITURE ORDER BY ID ASC")
    LiveData<List<Furniture>> getAllFurnitures();

    @Query("DELETE FROM FURNITURE")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFurnitures(List<Furniture> items);

}
