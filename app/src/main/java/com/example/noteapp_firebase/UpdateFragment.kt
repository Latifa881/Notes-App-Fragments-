package com.example.noteapp_firebase


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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UpdateFragment : Fragment() {
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java)}
    lateinit var updateLayout: LinearLayout
    lateinit var etYourNote:EditText
    lateinit var colorsRadioGroupUpdate:RadioGroup
    lateinit var rbUpdateRed:RadioButton
    lateinit var rbUpdateBlue:RadioButton
    lateinit var rbUpdateGreen:RadioButton
    lateinit var rbUpdateYellow:RadioButton
    lateinit var btUpdateNote:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        updateLayout = view.findViewById(R.id.updateLayout)
        etYourNote=view.findViewById(R.id.etYourNote)
        colorsRadioGroupUpdate=view.findViewById(R.id.colorsRadioGroupUpdate)
        rbUpdateRed=view.findViewById(R.id.rbUpdateRed)
        rbUpdateBlue=view.findViewById(R.id.rbUpdateBlue)
        rbUpdateGreen=view.findViewById(R.id.rbUpdateGreen)
        rbUpdateYellow=view.findViewById(R.id.rbUpdateYellow)
        btUpdateNote=view.findViewById(R.id.btUpdateNote)

        var noteColor = arguments?.getString("Color").toString()
        var note = arguments?.getString("Note").toString()
        val Id= arguments?.getString("Id").toString()

        changeBackGroundColor(noteColor, updateLayout)
        etYourNote.setText(note)

        colorsRadioGroupUpdate.setOnCheckedChangeListener { group, checkedId ->
            noteColor = changeBackGroundColor(checkedId, updateLayout)


        }
        btUpdateNote.setOnClickListener {
            val updatedNote = etYourNote.text.toString()
            if (updatedNote.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    myViewModel.updateNote(Note(Id, updatedNote, noteColor))
                }
                Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_updateFragment_to_homeFragment)

            } else {
                Toast.makeText(context, "You need to enter a note!", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun changeBackGroundColor(colorText: String, updateLayout: View) {
        when (colorText) {
            "Red" -> {
                rbUpdateRed.isChecked = true
                rbUpdateBlue.isChecked = false
                rbUpdateGreen.isChecked = false
                rbUpdateYellow.isChecked = false
                updateLayout.setBackgroundResource(R.drawable.round_layout_red)
            }
            "Blue" -> {
                rbUpdateRed.isChecked = false
                rbUpdateBlue.isChecked = true
                rbUpdateGreen.isChecked = false
                rbUpdateYellow.isChecked = false

                updateLayout.setBackgroundResource(R.drawable.round_layout_blue)
            }
            "Green" -> {
                rbUpdateRed.isChecked = false
                rbUpdateBlue.isChecked = false
                rbUpdateGreen.isChecked = true
                rbUpdateYellow.isChecked = false
                updateLayout.setBackgroundResource(R.drawable.round_layout_green)
            }
            "Yellow" -> {
                rbUpdateRed.isChecked = false
                rbUpdateBlue.isChecked = false
                rbUpdateGreen.isChecked = false
                rbUpdateYellow.isChecked = true
                updateLayout.setBackgroundResource(R.drawable.round_layout_yellow)
            }

        }

    }

    private fun changeBackGroundColor(checkedId: Int, updateLayout: View): String {
        var colorText = "Red"
        when (checkedId) {
            R.id.rbUpdateRed -> {
                updateLayout.setBackgroundResource(R.drawable.round_layout_red)
                colorText = "Red"
            }
            R.id.rbUpdateBlue -> {

                updateLayout.setBackgroundResource(R.drawable.round_layout_blue)
                colorText = "Blue"
            }
            R.id.rbUpdateGreen -> {

                updateLayout.setBackgroundResource(R.drawable.round_layout_green)
                colorText = "Green"
            }
            R.id.rbUpdateYellow -> {

                updateLayout.setBackgroundResource(R.drawable.round_layout_yellow)
                colorText = "Yellow"
            }

        }
        return colorText
    }


}