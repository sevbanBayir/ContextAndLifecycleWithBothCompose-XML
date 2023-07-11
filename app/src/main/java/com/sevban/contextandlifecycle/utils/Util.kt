
package com.sevban.contextandlifecycle.utils
/*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.sevban.contextandlifecycle.SecondActivity

//Activity context because its tied to UI but application context is not.
//Note: Normal dialogs don't get our activity to pause state BUT Permission Dialogs does.
val builder = AlertDialog.Builder(this)
builder.apply {
    setMessage("Sample activity dialog message")
    setPositiveButton(
        "OK"
    ) { dialog, id ->
        startActivity(Intent(applicationContext, SecondActivity::class.java))
    }
}
builder.create().show()

//Get activity to onPause() state.
ActivityCompat.requestPermissions(
this,
arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
100
)

// Accessing shared preferences using the application context
val sharedPreferences = this.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
sharedPreferences.edit().putString("name", "sevban").apply()

val name = sharedPreferences.getString("name", "")
Toast.makeText(this, "Name: $name", Toast.LENGTH_LONG).show()

//Application context can be used in a situation which a singleton is used that needs a context
//we MUST pass app context to that bcs
*/
