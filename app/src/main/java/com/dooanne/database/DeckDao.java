package com.dooanne.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.dooanne.model.Deck;

import java.util.List;

@Dao
public interface DeckDao {
    @Insert
    void insert(Deck deck);

    @Query("DELETE FROM deck_table")
    void deleteAll();

    @Update
        //(onConflict = OnConflictStrategy.REPLACE)
    void update(Deck deck);

    @Query("SELECT * FROM deck_table ORDER BY name ASC")
    LiveData<List<Deck>> getAllDecks();

    @Query("SELECT * FROM deck_table WHERE deck_id = :id")
    LiveData<Deck> getDeckById(int id);

    @Query("SELECT * FROM deck_table WHERE name LIKE '%' || :keyword || '%' OR " +
            "description LIKE '%' || :keyword || '%' ")
    LiveData<List<Deck>> searchDeck(String keyword);




}
