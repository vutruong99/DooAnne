package com.dooanne.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dooanne.database.DeckDao;
import com.dooanne.database.DeckRoomDatabase;
import com.dooanne.model.Deck;

import java.util.List;

public class DeckRepository {

    private DeckDao mDeckDao;
    private LiveData<List<Deck>> mAllDecks;
    private LiveData<Deck> mDeckById;

    public DeckRepository(Application application) {
        DeckRoomDatabase db = DeckRoomDatabase.getDatabase(application);
        mDeckDao = db.deckDao();
        mAllDecks = mDeckDao.getAllDecks();

    }

    public LiveData<List<Deck>> getAllDecks() {
        return mAllDecks;
    }

    public LiveData<Deck> getDeckById(int id) {
        return mDeckDao.getDeckById(id);
    }

    public void insert(final Deck deck) {
        DeckRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDeckDao.insert(deck);
            }
        });
    }


}

