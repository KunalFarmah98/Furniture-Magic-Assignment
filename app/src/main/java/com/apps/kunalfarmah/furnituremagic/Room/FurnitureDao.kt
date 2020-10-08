package com.apps.kunalfarmah.furnituremagic.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FurnitureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Furniture?)

    @get:Query("SELECT * from FURNITURE ORDER BY ID ASC")
    val allFurnitures: LiveData<List<Furniture?>?>

    @Query("DELETE FROM FURNITURE")
    fun deleteAll()

    @Delete()
    fun delete(item: Furniture?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFurnitures(items: List<Furniture?>?)
}