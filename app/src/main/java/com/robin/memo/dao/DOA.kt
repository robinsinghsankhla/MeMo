package com.robin.memo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface DOA {

    @Query("SELECT * FROM NOTETABLE ORDER BY date")
    fun getData():LiveData<List<com.robin.memo.model.ModelTable>>

    @Query("SELECT * FROM NOTETABLE WHERE priority = 3 ORDER BY date")
    fun getHighData():LiveData<List<com.robin.memo.model.ModelTable>>

    @Query("SELECT * FROM NOTETABLE WHERE priority = 2 ORDER BY date")
    fun getMediumData():LiveData<List<com.robin.memo.model.ModelTable>>

    @Query("SELECT * FROM NOTETABLE WHERE priority = 1 ORDER BY date")
    fun getLowData():LiveData<List<com.robin.memo.model.ModelTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)//for replace the same query
    fun insertNote(note:com.robin.memo.model.ModelTable)

    @Query("DELETE FROM NOTETABLE WHERE id=:id")
    fun deleteNote(id:Int)

    @Update
    fun updateNote(nots:com.robin.memo.model.ModelTable)

}