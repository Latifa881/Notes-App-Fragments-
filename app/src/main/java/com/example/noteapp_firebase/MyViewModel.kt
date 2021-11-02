package com.example.notesappFirebase_LiveData


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyViewModel(application: Application) : AndroidViewModel(application) {
    val db = Firebase.firestore
    private val notes: MutableLiveData<ArrayList<Note>> = MutableLiveData()
    private val notesArray: ArrayList<Note> = ArrayList()

    fun getNotesData(){
        notesArray.clear()
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var note = ""
                    var color = ""
                    Log.d("TAG123", "${document.id} => ${document.data}")
                    document.data.map { (key, value)
                        ->
                        when (key) {
                            "Note" -> note = value as String
                            "Color" -> color = value as String
                        }
                    }
                    Log.d("Result:::", "note:$note color:$color")
                    notesArray.add(Note( document.id,note, color))
                }
                notes.postValue(notesArray)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG123", "Error getting documents.", exception)
            }
    }

    fun getAllNotes(): LiveData<ArrayList<Note>> {
        return notes
    }
    fun addNote(noteObj: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            val myNewNote = hashMapOf(
                "Note" to noteObj.Note,
                "Color" to noteObj.Color,
            )

            db.collection("notes")
                .add(myNewNote)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    getNotesData()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }

    fun updateNote(noteObj: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            val myUpdatedNote = mapOf(
                "Note" to noteObj.Note,
                "Color" to noteObj.Color,
            )

            db.collection("notes").document(noteObj.id).update(myUpdatedNote)
                .addOnSuccessListener { documentReference ->
                    getNotesData()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }

    }

    fun deleteNote(noteObj: Note) {
        CoroutineScope(Dispatchers.IO).launch {

            db.collection("notes").document(noteObj.id).delete()
                .addOnSuccessListener { documentReference ->
                    getNotesData()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error deletimg document", e)
                }
        }

    }
}