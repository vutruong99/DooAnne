package com.dooanne;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dooanne.model.Card;
import com.dooanne.model.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Deck.class}, version = 2, exportSchema = false)
@TypeConverters({CardConverter.class})
public abstract class DeckRoomDatabase extends RoomDatabase {
    public abstract DeckDao deckDao();
    private static DeckRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DeckRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeckRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DeckRoomDatabase.class, "deck_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                //Pre-populate the data in the background
                DeckDao dao = INSTANCE.deckDao();

                ArrayList<String> brands = new ArrayList<>();
                brands.add("Adidas");
                brands.add("Nike");
                brands.add("Highlands");

                ArrayList<String> food = new ArrayList<>();

                dao.insert(new Deck(1,"brands","lol",R.drawable.brand,false,1,brands));
                dao.insert(new Deck(2,"animals","lol",R.drawable.diet,false,1,food));
            });
        }
    };
}
