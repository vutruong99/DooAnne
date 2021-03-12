package com.dooanne.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dooanne.R;
import com.dooanne.model.Deck;

import java.util.ArrayList;
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
                            .createFromAsset("databases/deck_database.db")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) { // From version 1 to version 2
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };


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

                dao.insert(new Deck(1,"lol","lol", R.drawable.diet,"#123123",false,1,brands));
            });
        }
    };
}
