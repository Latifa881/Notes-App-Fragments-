package com.example.noteapp_firebase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappFirebase_LiveData.MyViewModel
import com.example.notesappFirebase_LiveData.Note
import com.example.notesappFirebase_LiveData.RecyclerViewAdapter


class HomeFragment : Fragment() {
    lateinit var rvMain: RecyclerView
    var notesArray = ArrayList<Note>()
    lateinit var myAdapter: RecyclerViewAdapter
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

//    lateinit var myContext:Context
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        myContext=context
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        rvMain =view.findViewById(R.id.rvMain)
        myViewModel.getNotesData()

        myAdapter = RecyclerViewAdapter(notesArray, this)
        rvMain.adapter = myAdapter
        rvMain.layoutManager = LinearLayoutManager(context)

        myViewModel.getAllNotes().observe(viewLifecycleOwner, { notes -> myAdapter.update(notes) })



        return view
    }


}