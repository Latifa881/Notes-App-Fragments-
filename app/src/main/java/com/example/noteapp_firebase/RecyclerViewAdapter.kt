package com.example.notesappFirebase_LiveData

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp_firebase.HomeFragment
import com.example.noteapp_firebase.R
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecyclerViewAdapter(private var notes: List<Note>, val homeFragment: HomeFragment) :
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val myViewModel by lazy { ViewModelProvider(homeFragment).get(MyViewModel::class.java) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val data = notes[position]
        holder.itemView.apply {

            tvNote.text = data.Note
            when (data.Color) {
                "Red" -> linearLayout.setBackgroundResource(R.drawable.round_layout_red)
                "Blue" -> linearLayout.setBackgroundResource(R.drawable.round_layout_blue)
                "Green" -> linearLayout.setBackgroundResource(R.drawable.round_layout_green)
                "Yellow" -> linearLayout.setBackgroundResource(R.drawable.round_layout_yellow)
            }
            ivEdit.setOnClickListener {
                val bundle = bundleOf(
                    "Id" to data.id,
                    "Color" to data.Color,
                    "Note" to data.Note
                )
                Navigation.findNavController(homeFragment.requireView())
                    .navigate(R.id.action_homeFragment_to_updateFragment, bundle)
            }
            ivDelete.setOnClickListener {
                val deleteBuilder = AlertDialog.Builder(context)
                deleteBuilder.setTitle("Delete note")
                deleteBuilder.setMessage("Are you sure you want to delete this note?")
                deleteBuilder.setIcon(android.R.drawable.ic_dialog_alert)

                deleteBuilder.setPositiveButton("Delete") { dialogInterface, which ->
                    CoroutineScope(Dispatchers.IO).launch {
                        myViewModel.deleteNote(Note(data.id, data.Note, data.Color))

                    }

                    dialogInterface.dismiss()
                }
                //performing cancel action
                deleteBuilder.setNeutralButton("Cancel") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                deleteBuilder.setCancelable(true)
                deleteBuilder.show()
            }

        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: List<Note>) {
        println("UPDATING DATA")
        this.notes = notes
        notifyDataSetChanged()
    }


}
