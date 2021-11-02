package com.example.noteapp_firebase

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.notesappFirebase_LiveData.MyViewModel
import com.example.notesappFirebase_LiveData.Note



class AddFragment : Fragment() {
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java)}
    lateinit var colorsRadioGroup: RadioGroup
    lateinit var addLayout:LinearLayout
    lateinit var btAddNote:Button
    lateinit var btViewNotes:Button
    lateinit var etNote:EditText
    lateinit var rbRed:RadioButton
    lateinit var rbBlue:RadioButton
    lateinit var rbGreen:RadioButton
    lateinit var rbYellow:RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        var note = ""
        var colorText = "Red"
        colorsRadioGroup=view.findViewById(R.id.colorsRadioGroup)
        addLayout=view.findViewById(R.id.addLayout)
        btAddNote=view.findViewById(R.id.btAddNote)
        btViewNotes=view.findViewById(R.id.btViewNotes)
        etNote=view.findViewById(R.id.etNote)
        rbRed=view.findViewById(R.id.rbRed)
        rbBlue=view.findViewById(R.id.rbBlue)
        rbGreen=view.findViewById(R.id.rbGreen)
        rbYellow=view.findViewById(R.id.rbYellow)
        colorsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbRed -> {
                    colorText = "Red"
                    addLayout.setBackgroundResource(R.drawable.round_layout_red)
                }
                R.id.rbBlue -> {
                    colorText = "Blue"
                    addLayout.setBackgroundResource(R.drawable.round_layout_blue)
                }
                R.id.rbGreen -> {
                    colorText = "Green"
                    addLayout.setBackgroundResource(R.drawable.round_layout_green)
                }
                R.id.rbYellow -> {
                    colorText = "Yellow"
                    addLayout.setBackgroundResource(R.drawable.round_layout_yellow)
                }

            }

        }
        btAddNote.setOnClickListener {

            note =etNote.text.toString()
            if (note.isNotEmpty()) {
                myViewModel.addNote(Note( "",note, colorText))
                Toast.makeText(context,"The note is added successfully",Toast.LENGTH_SHORT).show()
             etNote.setText("")
              rbRed.isChecked = true
             rbBlue.isChecked = false
             rbGreen.isChecked = false
                rbYellow.isChecked = false

            }
        }
        btViewNotes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_homeFragment)
        }


        return view
    }

}