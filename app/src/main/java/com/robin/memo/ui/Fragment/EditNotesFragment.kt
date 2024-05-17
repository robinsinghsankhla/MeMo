package com.robin.memo.ui.Fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.robin.memo.R
import com.robin.memo.ViewModel.NoteViewModel
import com.robin.memo.databinding.FragmentEditNotesBinding
import com.robin.memo.model.ModelTable
import java.util.Date


class EditNotesFragment : Fragment(),View.OnClickListener {


    lateinit var binding: FragmentEditNotesBinding
    val notes by navArgs<EditNotesFragmentArgs>()//get data from the HomeFragment
    val viewModel: NoteViewModel by viewModels()//by this we don't need to initialize this
    lateinit var priority:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);//to set menu item in the fragment
        //get the old prioritu
        priority = notes.Data.priority
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)

//        Log.d("@@@@",notes.Data.title)
        //set data to views
        binding.apply {
            textTitle.setText(notes.Data.title)
            textSubtitle.setText(notes.Data.subTitle)
            textNote.setText(notes.Data.note)

            //set priority
            when (notes.Data.priority) {
                "1" -> this.priorityLow.setImageResource(R.drawable.baseline_download_done_24)
                "2" -> this.priorityMedium.setImageResource(R.drawable.baseline_download_done_24)
                "3" -> this.priorityHigh.setImageResource(R.drawable.baseline_download_done_24)
            }

            //on click addBtn
            this.btnAddNotesDone.setOnClickListener(this@EditNotesFragment)

            //update the priority
            priorityLow.setOnClickListener {low ->
                priority = "1"
                priorityLow.setImageResource(R.drawable.baseline_download_done_24)
                priorityMedium.setImageResource(0)
                priorityHigh.setImageResource(0)

            }
            priorityMedium.setOnClickListener { medium ->
                priority = "2"
                priorityMedium.setImageResource(R.drawable.baseline_download_done_24)
                priorityLow.setImageResource(0)
                priorityHigh.setImageResource(0)
            }
            priorityHigh.setOnClickListener { high ->
                priority = "3"
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
            id = notes.Data.id,
            title = title,
            subTitle = subTitle,
            note = note,
            date = noteDate,
            priority = priority
        )
        viewModel.updateNote(noteModel)
//        Log.d("@@@@","data added")
        Toast.makeText(requireContext(), "Memo Updated Successfully", Toast.LENGTH_SHORT).show()
        //navigate to home page
        Navigation.findNavController(v!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }
    //set the delete_menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //action on delelte menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val bottomSheet = BottomSheetDialog(requireContext(),R.style.BottomSheetTheme)
        bottomSheet.setContentView(R.layout.buttom_drawel_dialog)
        //find id of the dialog button
        val btnYes = bottomSheet.findViewById<Button>(R.id.yesbtn)
        val btnNo = bottomSheet.findViewById<Button>(R.id.nobtn)

        //set click on delete menu
        if (item.itemId == R.id.delete_menu){
//            Log.d("@@@@@","btn clicked")

            //on click yes butoon
            btnYes?.setOnClickListener{
                viewModel.deleteNotee(notes.Data.id!!)
                try {
                    Navigation.findNavController(requireView()).navigate("action_editNotesFragment_to_homeFragment")
                }catch (e:Exception){
                    Log.d("@@@@@",e.message.toString())
                }
                bottomSheet.dismiss()
            }
            btnNo?.setOnClickListener {
                bottomSheet.dismiss()
            }


            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)
    }


}