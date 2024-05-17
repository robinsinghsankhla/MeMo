package com.robin.memo.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.robin.memo.DataBase.NoteDatabase
import com.robin.memo.Repository.NoteRepository
import com.robin.memo.model.ModelTable


class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val repo: NoteRepository
    init {
        val dao = NoteDatabase.getDatabaseInstance(application).getNoteDoa()
        repo = NoteRepository(dao)
    }
    //get data
    fun getNotes() : LiveData<List<ModelTable>> = repo.getAllNotes()

    //highPriority data
    fun getHighNotes() : LiveData<List<ModelTable>> = repo.getHighNotes()

    //mudiumPriority data
    fun getMediumNotes() : LiveData<List<ModelTable>> = repo.getMediumNotes()

    //lowPriority data
    fun getLowNotes() : LiveData<List<ModelTable>> = repo.getLowNotes()

    //delete data
    fun deleteNotee(id:Int){
        repo.deleteNotee(id)
    }

    //update data
    fun updateNote(note:ModelTable){
        repo.updateNote(note)
    }

    //insert data
    fun addNote(note:ModelTable){
        repo.addNote(note)
    }


}