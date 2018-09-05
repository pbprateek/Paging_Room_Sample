package com.prateek.room.storewords;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    private WordDao wordDao;

    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application){
        WordRoomDatabase db= WordRoomDatabase.getDatabase(application);
        wordDao=db.wordDao();
        mAllWords=wordDao.getAllWords();

    }

    public LiveData<List<Word>> getmAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        new insertAsyncTask(wordDao).execute(word);
    }



    private static class insertAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao mAsyncWordDao;

        insertAsyncTask(WordDao dao){
            mAsyncWordDao=dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncWordDao.insert(words[0]);
            return null;
        }
    }
}
