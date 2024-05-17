package com.robin.memo.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.robin.memo.R
import com.robin.memo.databinding.ItemNoteBinding
import com.robin.memo.model.ModelTable
import com.robin.memo.ui.Fragment.HomeFragment
import com.robin.memo.ui.Fragment.HomeFragmentDirections

class NoteAdapter(var list: List<ModelTable>, val context: Context) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getItem(list[position])
        //move to editMeMo
        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(list[position])
            try {
                Navigation.findNavController(it!!).navigate(action)
            }catch (e:Exception){
                Log.d("@@@@",e.message.toString())
            }
        }
        //to delete the notes
        holder.binding.root.setOnLongClickListener {view ->
            HomeFragment().deleteNote(list[position].id,context,view)
            true

        }
    }

    fun searchFun(newList: ArrayList<ModelTable>) {
        list  = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun getItem(notes : ModelTable){
           binding.apply {
               title.text = notes.title
               textSubtitle.text = notes.subTitle
               date.text = notes.date
               //set priority view

               when(notes.priority){
                   "1" -> this.priorityView.setBackgroundResource(R.drawable.lowpri_bg)
                   "2" -> this.priorityView.setBackgroundResource(R.drawable.mediumpri_bg)
                   "3" -> this.priorityView.setBackgroundResource(R.drawable.highpri_bg)
               }
           }
        }
    }
}