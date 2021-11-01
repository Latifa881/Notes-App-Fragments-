package com.example.notesappFirebase_LiveData

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp_firebase.R

import kotlinx.android.synthetic.main.dialogue_view_add.view.*


class MainActivity : AppCompatActivity() {
    lateinit var rvMain: RecyclerView
    var notesArray = ArrayList<Note>()
    lateinit var myAdapter: RecyclerViewAdapter
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain = findViewById(R.id.rvMain)
        myViewModel.getNotesData()

        myAdapter = RecyclerViewAdapter(notesArray, this)
        rvMain.adapter = myAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        myViewModel.getAllNotes().observe(this, { notes -> myAdapter.update(notes) })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.addNote -> {
                var note = ""
                var colorText = "Red"
                val builder = AlertDialog.Builder(this)
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_view_add, null)
                builder.setView(dialogView)
                val alertDialog: AlertDialog = builder.create()

                dialogView.colorsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.rbRed -> {
                            colorText = "Red"
                            dialogView.alertLayout.setBackgroundResource(R.drawable.round_layout_red)
                        }
                        R.id.rbBlue -> {
                            colorText = "Blue"
                            dialogView.alertLayout.setBackgroundResource(R.drawable.round_layout_blue)
                        }
                        R.id.rbGreen -> {
                            colorText = "Green"
                            dialogView.alertLayout.setBackgroundResource(R.drawable.round_layout_green)
                        }
                        R.id.rbYellow -> {
                            colorText = "Yellow"
                            dialogView.alertLayout.setBackgroundResource(R.drawable.round_layout_yellow)
                        }

                    }

                }
                dialogView.btAddNote.setOnClickListener {

                    note = dialogView.etNote.text.toString()
                    if (note.isNotEmpty()) {
                        myViewModel.addNote(Note( "",note, colorText))

                        alertDialog.dismiss()
                        dialogView.etNote.setText("")
                        dialogView.rbRed.isChecked = true
                        dialogView.rbBlue.isChecked = false
                        dialogView.rbGreen.isChecked = false
                        dialogView.rbYellow.isChecked = false

                    }
                }

                alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

                // Set other dialog properties
                alertDialog.setCancelable(true)
                alertDialog.show()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


}