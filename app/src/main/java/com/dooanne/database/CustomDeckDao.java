package com.dooanne.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dooanne.model.Deck;

import java.util.List;

@Dao
public interface CustomDeckDao {
    @Insert
    void insert(Deck deck);

    @Query("DELETE FROM custom_deck_table")
    void deleteAll();

    @Query("SELECT * FROM custom_deck_table ORDER BY name ASC")
    LiveData<List<Deck>> getAllDecks();

    @Query("SELECT * FROM custom_deck_table WHERE deck_id = :id")
    LiveData<Deck> getDeckById(int id);

}
