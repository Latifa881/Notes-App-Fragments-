package com.example.notesappFirebase_LiveData


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import com.example.noteapp_firebase.R



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.addNote -> {
                Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_addFragment)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


}