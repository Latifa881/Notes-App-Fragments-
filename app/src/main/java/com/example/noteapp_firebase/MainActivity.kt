package com.example.notesappFirebase_LiveData


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
               val id= Navigation.findNavController(this,R.id.fragmentContainerView).currentDestination?.getId()
               // Toast.makeText(this,"${R.id.homeFragment}=$id",Toast.LENGTH_LONG).show()
                when(id){
                    R.id.homeFragment-> Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.action_homeFragment_to_addFragment)
                    R.id.updateFragment->Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.action_updateFragment_to_addFragment)
                }
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


}