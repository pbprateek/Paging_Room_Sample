package com.prateek.room.storewords;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static WordRoomDatabase wordRoomDatabase;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(wordRoomDatabase).execute();
                }
            };


    public static WordRoomDatabase getDatabase(final Context context) {

        if (wordRoomDatabase == null) {
            synchronized (WordRoomDatabase.class) {
                if (wordRoomDatabase == null) {

                    wordRoomDatabase = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)  only uncomment if u want to delete data when u restart the app
                            .build();

                }
            }
        }

        return wordRoomDatabase;

    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                mDao.insert(word);
            }
            return null;
        }
    }


}


//It is an instance which is the singleton and can be accessed in an activity via getApplicationContext().
// This context is tied to the lifecycle of an application. The application context can be used where you need a context
// whose lifecycle is separate from the current context or when you are passing a context beyond the scope of an activity
