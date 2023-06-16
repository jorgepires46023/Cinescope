package com.example.cinescope.ui.bottombar

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class NavController(private val context: Context) {
    fun navigate(cls: Class<*>){
        val intent = Intent(context, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        /** FLAG_ACTIVITY_NEW_TASK:
         * starts the activity in a new task, if a task is already running
         * for the activity being started, that task is brought to the foreground
         * with its last state restored
         * **/
        try {
            startActivity(context, intent, null)
        }catch(e: ActivityNotFoundException){
            Toast.makeText(context, "Activity not found", Toast.LENGTH_LONG).show()
        }
    }
}