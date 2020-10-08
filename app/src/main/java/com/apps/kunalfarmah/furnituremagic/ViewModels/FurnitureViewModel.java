package com.apps.kunalfarmah.furnituremagic.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.apps.kunalfarmah.furnituremagic.Repositories.FurnitureRepository;
import com.apps.kunalfarmah.furnituremagic.Room.Furniture;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FurnitureViewModel extends AndroidViewModel {

    private FurnitureRepository postRoomDBFurnituresitory;
    private LiveData<List<Furniture>> mAllFurnitures;

    public FurnitureViewModel(Application application) {
        super(application);
        postRoomDBFurnituresitory = new FurnitureRepository(application);
    }

    public LiveData<List<Furniture>> getFurnitures() {
        mAllFurnitures = postRoomDBFurnituresitory.getFurnitures();
        return mAllFurnitures;
    }

    public void insertItem(@NotNull Furniture furniture) {
        postRoomDBFurnituresitory.insert(furniture);
    }
}
