package com.robin.memo.Repository

import androidx.lifecycle.LiveData
import com.robin.memo.dao.DOA
import com.robin.memo.model.ModelTable

class NoteRepository(val doa : DOA) {
    //fetch data
    fun getAllNotes() : LiveData<List<ModelTable>> = doa.getData()

    //highPriority data
    fun getHighNotes() : LiveData<List<ModelTable>> = doa.getHighData()

    //mudiumPriority data
    fun getMediumNotes() : LiveData<List<ModelTable>> = doa.getMediumData()

    //lowPriority data
    fun getLowNotes() : LiveData<List<ModelTable>> = doa.getLowData()

    //delete data
    fun deleteNotee(id:Int){
        doa.deleteNote(id)
    }

    //update data
    fun updateNote(note:ModelTable){
        doa.updateNote(note)
    }

    //insert data
    fun addNote(note:ModelTable){
        doa.insertNote(note)
    }
}