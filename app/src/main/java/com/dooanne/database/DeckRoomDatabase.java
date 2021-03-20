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
import com.dooanne.model.CustomDeck;
import com.dooanne.model.Deck;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Deck.class, CustomDeck.class}, version = 1, exportSchema = false)
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
                            DeckRoomDatabase.class, "decks_database").build();
                }
            }
//                            .createFromAsset("databases/deck_database.db")
//                            .addMigrations(MIGRATION_1_2)
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) { // From version 1 to version 2
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                DeckDao dao = INSTANCE.deckDao();
                dao.deleteAll();
            });
        }
    };
}
