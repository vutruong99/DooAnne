package com.dooanne.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dooanne.model.Deck;
import com.dooanne.repos.DeckRepository;

import java.util.ArrayList;
import java.util.List;


public class DeckViewModel extends AndroidViewModel {

    private DeckRepository mRepository;

    private LiveData<List<Deck>> mAllDecks;

    public DeckViewModel (Application application) {
        super(application);
        mRepository = new DeckRepository(application);
        mAllDecks = mRepository.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() { return mAllDecks; }

    public LiveData<Deck> getDeckById(int id) {
        return mRepository.getDeckById(id);
    }

    public void insert(Deck deck) { mRepository.insert(deck); }

    public LiveData<List<Deck>> getSearchedDecks(String keyword) {
        return mRepository.searchDeck(keyword);
    }

}
