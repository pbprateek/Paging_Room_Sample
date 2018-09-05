package com.prateek.room.storewords;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


//Dao for Room(Data Access Object)

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    //There is no convenience annotation for deleting multiple entities so provide query manually


    @Query("DELETE FROM word_table")
    void deleteAll();


    @Query("SELECT * FROM word_table ORDER BY word ASC")   //ASC-ascending   DESC-DESCENDING ,order by word column in ASC
    LiveData<List<Word>> getAllWords();
}
