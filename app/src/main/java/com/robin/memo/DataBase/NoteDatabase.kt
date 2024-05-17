package com.robin.memo.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import com.robin.memo.dao.DOA


import com.robin.memo.model.ModelTable

@Database(entities = [ModelTable::class], exportSchema = false, version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDoa() : DOA//to the doa function to fetch table data

    companion object{

        @Volatile//to eassy to fetch
        var INSTANCE : NoteDatabase?=null

        val tempInstace = INSTANCE
        fun getDatabaseInstance(context: Context ) : NoteDatabase{
            if ( tempInstace != null)
                return tempInstace

            synchronized(this){
                val roomDatabase =
                    Room.databaseBuilder(context,NoteDatabase::class.java,DatabaseName.DATABASENAME).allowMainThreadQueries().build()
                INSTANCE = roomDatabase
                return roomDatabase
            }

        }
    }
}