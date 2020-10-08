package com.apps.kunalfarmah.furnituremagic.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.apps.kunalfarmah.furnituremagic.Repositories.FurnitureRepository
import com.apps.kunalfarmah.furnituremagic.Room.Furniture

class FurnitureViewModel(application: Application) : AndroidViewModel(application) {
    private val postRoomDBFurnituresitory: FurnitureRepository
    private var mAllFurnitures: LiveData<List<Furniture?>?>? = null
    val furnitures: LiveData<List<Furniture?>?>
        get() {
            mAllFurnitures = postRoomDBFurnituresitory.furnitures
            return mAllFurnitures!!
        }

    fun insertItem(furniture: Furniture) {
        postRoomDBFurnituresitory.insert(furniture)
    }

    fun deleteItem(furniture: Furniture?) {
        postRoomDBFurnituresitory.delete(furniture)
    }

    fun deleteALL() {
        postRoomDBFurnituresitory.deleteAll()
    }

    init {
        postRoomDBFurnituresitory = FurnitureRepository(application)
    }
}