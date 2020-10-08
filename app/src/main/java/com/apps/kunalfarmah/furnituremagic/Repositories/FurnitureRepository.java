package com.apps.kunalfarmah.furnituremagic.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.apps.kunalfarmah.furnituremagic.Room.Furniture;
import com.apps.kunalfarmah.furnituremagic.Room.FurnitureDao;
import com.apps.kunalfarmah.furnituremagic.Room.FurnitureRoomDataBase;

import java.util.List;

public class FurnitureRepository {
    private FurnitureDao postDao;
    private LiveData<List<Furniture>> posts;

    public FurnitureRepository(Application application) {
        FurnitureRoomDataBase db = FurnitureRoomDataBase.getInstance(application);
        postDao = db.FurnitureDao();
        posts = postDao.getAllFurnitures();
    }

    public void insert(Furniture post) {
        new InsertFurnitureAsyncTask(postDao).execute(post);
    }

    public LiveData<List<Furniture>> getFurnitures() {
        return posts;
    }

    public static class InsertFurnitureAsyncTask extends AsyncTask<Furniture, Void, Void> {
        private FurnitureDao postDao;

        private InsertFurnitureAsyncTask(FurnitureDao postDao) {
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Furniture... posts) {
            postDao.insert(posts[0]);
            return null;
        }
    }

    public void insertFurnitures(List<Furniture> postlist) {
        new insertAsyncTask(postDao).execute(postlist);
    }

    private static class insertAsyncTask extends AsyncTask<List<Furniture>, Void, Void> {

        private FurnitureDao mAsyncTaskDao;

        insertAsyncTask(FurnitureDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Furniture>... params) {
            mAsyncTaskDao.insertFurnitures(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllAsyncTask(postDao).execute();

    }

    private static class deleteAllAsyncTask extends AsyncTask<List<Furniture>, Void, Void> {

        private FurnitureDao mAsyncTaskDao;

        deleteAllAsyncTask(FurnitureDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Furniture>... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
