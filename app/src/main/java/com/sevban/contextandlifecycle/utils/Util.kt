
package com.sevban.contextandlifecycle.utils

/**

Accessing shared preferences using the application context
val sharedPreferences = this.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
sharedPreferences.edit().putString("name", "sevban").apply()

val name = sharedPreferences.getString("name", "")
Toast.makeText(this, "Name: $name", Toast.LENGTH_LONG).show()

Application context can be used in a situation which a singleton is used that needs a context
we MUST pass app context to that bcs
*/

