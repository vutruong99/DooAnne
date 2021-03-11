package com.dooanne.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dooanne.DeckDao;
import com.dooanne.DeckRoomDatabase;
import com.dooanne.model.Deck;

import java.util.List;

public class DeckRepository {

    private DeckDao mDeckDao;
    private LiveData<List<Deck>> mAllDecks;

    public DeckRepository(Application application) {
        DeckRoomDatabase db = DeckRoomDatabase.getDatabase(application);
        mDeckDao = db.deckDao();
        mAllDecks = mDeckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return mAllDecks;
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

