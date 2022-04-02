package com.example.ecosystemoffice

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton

class Settings : AppCompatActivity() {
    var save: Button? = null
    var editText: EditText? = null
    var link: String? = null
    var context: Context? = null
    var textFromPreferences: String? = null
    private var preferences: SharedPreferences? = null
    private fun saveData(key: String, values: String) {
        val preferencesEditor = preferences!!.edit()
        preferencesEditor.putString(key, values)
        preferencesEditor.commit()
    }
    private fun restoreData(key: String) {
        textFromPreferences = preferences!!.getString(key, "")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        editText = findViewById<View>(R.id.editText1) as EditText
        save = findViewById<View>(R.id.button1) as Button
        val onOff = findViewById<View>(R.id.togglebutton) as ToggleButton
        restoreData(PREFERENCES_LINK)
        if (textFromPreferences != null) {
            editText!!.hint = "Now: $textFromPreferences"
        }
        restoreData(PREFERENCES_SERVICE)
        if (textFromPreferences == "true") {
            onOff.isChecked = true
        }
        if (textFromPreferences == "false") {
            onOff.isChecked = false
        }
        val save1 = View.OnClickListener {
            val link = editText!!.text.toString()
            saveData(PREFERENCES_LINK, link)
            val context = applicationContext
            val text: CharSequence = "Saved"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }
        save!!.setOnClickListener(save1)
    }
    fun onToggleClicked(view: View) {
        val on = (view as ToggleButton).isChecked
        if (on) {
            startService(Intent(this@Settings, MyService::class.java))
        } else {
            stopService(Intent(this@Settings, MyService::class.java))
        }
    }
    companion object {
        private const val PREFERENCES_NAME = "myPreferences"
        private const val PREFERENCES_LINK = "link"
        private const val PREFERENCES_SERVICE = "service"
    }
}