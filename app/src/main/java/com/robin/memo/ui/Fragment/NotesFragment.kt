package com.robin.memo.ui.Fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.robin.memo.R
import com.robin.memo.ViewModel.NoteViewModel
import com.robin.memo.databinding.FragmentNotesBinding
import com.robin.memo.model.ModelTable


import java.util.Date


class NotesFragment : Fragment(),View.OnClickListener{

    lateinit var binding : FragmentNotesBinding
    val viewModel: NoteViewModel by viewModels()//by this we don't need to initialize this
    var priority = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentNotesBinding.inflate(layoutInflater,container,false)

        binding.btnAddNotesDone.setOnClickListener(this@NotesFragment)
        binding.apply {
            priorityLow.setOnClickListener {low ->
                priority = 1
                priorityLow.setImageResource(R.drawable.baseline_download_done_24)
                priorityMedium.setImageResource(0)
                priorityHigh.setImageResource(0)

            }
            priorityMedium.setOnClickListener { medium ->
                priority = 2
                priorityMedium.setImageResource(R.drawable.baseline_download_done_24)
                priorityLow.setImageResource(0)
                priorityHigh.setImageResource(0)
            }
            priorityHigh.setOnClickListener { high ->
                priority = 3
                priorityHigh.setImageResource(R.drawable.baseline_download_done_24)
                priorityMedium.setImageResource(0)
                priorityLow.setImageResource(0)
            }
        }




        return binding.root
    }

    override fun onClick(v: View?) {
        val title = binding.textTitle.text.toString()
        val subTitle = binding.textSubtitle.text.toString()
        val note = binding.textNote.text.toString()
        //current data
        val d = Date()
        val noteDate= DateFormat.format("MMMM d, yyyy ", d.time).toString()
        Log.d("@@@@", noteDate)

        //add note to view model
        val noteModel = ModelTable(
            null,
            title = title,
            subTitle = subTitle,
            note = note,
            date = noteDate,
            priority = priority.toString()
        )
        viewModel.addNote(noteModel)
//        Log.d("@@@@","data added")
        Toast.makeText(requireContext(), "Memo Created Successfully", Toast.LENGTH_SHORT).show()
        //navigate to home page
        Navigation.findNavController(v!!).navigate(R.id.action_notesFragment_to_homeFragment)


    }


}