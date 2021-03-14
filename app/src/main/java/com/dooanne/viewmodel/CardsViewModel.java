package com.dooanne.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dooanne.model.Deck;

public class CardsViewModel extends ViewModel {
    private MutableLiveData<Deck> mCurrentDeck = new MutableLiveData<>();

    public void setCurrentDeck(Deck deck) {
        mCurrentDeck.setValue(deck);
    }

    public LiveData<Deck> getCurrentDeck() {
        return mCurrentDeck;
    }
}
