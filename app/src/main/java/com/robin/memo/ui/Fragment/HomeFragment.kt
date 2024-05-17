package com.robin.memo.ui.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.robin.memo.R
import com.robin.memo.ViewModel.NoteViewModel
import com.robin.memo.databinding.FragmentHomeBinding
import com.robin.memo.model.ModelTable
import com.robin.memo.ui.Adapter.NoteAdapter


class HomeFragment : Fragment(),View.OnClickListener {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NoteViewModel by viewModels()
    lateinit var oldList : ArrayList<ModelTable>
    lateinit var adapter: NoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        binding.btnAddNotes.setOnClickListener(this@HomeFragment)

        //get data from the database
        viewModel.getNotes().observe(viewLifecycleOwner, Observer {notesList ->
            //set data to old list
            oldList = notesList as ArrayList
            //set recycleView
            binding.apply {
                recNotes.layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
                adapter = NoteAdapter(notesList,requireContext())
                recNotes.adapter = adapter

            }
        })

        //get data according to the fillter set
        binding.apply {
            //get all data
            fillterAll.setOnClickListener {
                viewModel.getNotes().observe(viewLifecycleOwner, Observer {notesList ->
                    //set data to old list
                    oldList = notesList as ArrayList
                    //set recycleView
                    recNotes.layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
                    adapter = NoteAdapter(notesList,requireContext())
                    recNotes.adapter = adapter
                })
            }
            // get high priority data
            fillterHigh.setOnClickListener {
                viewModel.getHighNotes().observe(viewLifecycleOwner, Observer {notesList ->
                    //set data to old list
                    oldList = notesList as ArrayList
                    //set recycleView
                    recNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                    adapter = NoteAdapter(notesList,requireContext())
                    recNotes.adapter = adapter
                })
            }
            //get medium priority data
            fillterMedium.setOnClickListener {
                viewModel.getMediumNotes().observe(viewLifecycleOwner, Observer {notesList ->
                    //set data to old list
                    oldList = notesList as ArrayList
                    //set recycleView
                    recNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                    recNotes.adapter = NoteAdapter(notesList,requireContext())
                })
            }
            //get low priority data
            fillterLow.setOnClickListener {
                viewModel.getLowNotes().observe(viewLifecycleOwner, Observer {notesList ->
                    //set data to old list
                    oldList = notesList as ArrayList
                    //set recycleView
                    recNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                    adapter = NoteAdapter(notesList,requireContext())
                    recNotes.adapter = adapter
                })
            }
        }
        return binding.root
    }

    override fun onClick(v: View?) {
        Navigation.findNavController(v!!).navigate(R.id.action_homeFragment_to_notesFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Search Notes"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNote(newText)
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchNote(newText: String?) {
        val newList : ArrayList<ModelTable>
        newList = arrayListOf()

        for (i in oldList){
            if (i.title.contains(newText!!) || i.subTitle.contains(newText!!)){
                newList.add(i)
            }
        }
        adapter.searchFun(newList)
    }

    fun deleteNote(id: Int?, context: Context, view: View) {
            val bottomSheet = BottomSheetDialog(context,R.style.BottomSheetTheme)
            bottomSheet.setContentView(R.layout.buttom_drawel_dialog)
            //find id of the dialog button
            val btnYes = bottomSheet.findViewById<Button>(R.id.yesbtn)
            val btnNo = bottomSheet.findViewById<Button>(R.id.nobtn)
            //on click yes butoon
                btnYes?.setOnClickListener{

                    try {
                        viewModel.deleteNotee(id!!)
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
    }
