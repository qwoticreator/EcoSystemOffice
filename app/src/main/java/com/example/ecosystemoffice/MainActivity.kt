package com.example.ecosystemoffice

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var tempIn: TextView? = null
    var tempOut: TextView? = null
    var window: TextView? = null
    var move: TextView? = null
    var rain: TextView? = null
    var time: TextView? = null
    var button1: Button? = null
    var context: Context? = null
    var tempin = 0.0
    var tempout = 0.0
    var Imove = 0
    var net = false
    var Stempin: String? = null
    var Stempout: String? = null
    var Swindow: String? = null
    var Smove: String? = null
    var Srain: String? = null
    var link: String? = null
    var textFromPreferences: String? = null
    private var preferences: SharedPreferences? = null

    inner class TempIn : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val url = URL("http", link, 80, "/tempin")
                val conn = url.openConnection()
                conn.doInput = true
                conn.connect()
                Thread.sleep(500)
                val sb = StringBuilder()
                val isr = InputStreamReader(conn.getInputStream())
                val `in` = BufferedReader(isr)
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                `in`.close()
                tempin = sb.toString().toDouble()
                Stempin = java.lang.Double.toString(tempin / 100.00)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    inner class TempOut : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val url = URL("http", link, 80, "/tempout")
                val conn = url.openConnection()
                conn.doInput = true
                conn.connect()
                Thread.sleep(500)
                val sb = StringBuilder()
                val isr = InputStreamReader(conn.getInputStream())
                val `in` = BufferedReader(isr)
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                `in`.close()
                tempout = sb.toString().toDouble()
                Stempout = java.lang.Double.toString(tempout / 100.00)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    inner class Window : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val url = URL("http", link, 80, "/window")
                val conn = url.openConnection()
                conn.doInput = true
                conn.connect()
                Thread.sleep(500)
                val sb = StringBuilder()
                val isr = InputStreamReader(conn.getInputStream())
                val `in` = BufferedReader(isr)
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                `in`.close()
                Swindow = sb.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    inner class Moves : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val url = URL("http", link, 80, "/alarm")
                val conn = url.openConnection()
                conn.doInput = true
                conn.connect()
                Thread.sleep(500)
                val sb = StringBuilder()
                val isr = InputStreamReader(conn.getInputStream())
                val `in` = BufferedReader(isr)
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                `in`.close()
                Smove = sb.toString()
                Imove = Smove!!.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    inner class Rain : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val url = URL("http", link, 80, "/rain")
                val conn = url.openConnection()
                conn.doInput = true
                conn.connect()
                Thread.sleep(500)
                val sb = StringBuilder()
                val isr = InputStreamReader(conn.getInputStream())
                val `in` = BufferedReader(isr)
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                `in`.close()
                Srain = sb.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    private fun saveData(key: String, values: String) {
        val preferencesEditor = preferences!!.edit()
        preferencesEditor.putString(key, values)
        preferencesEditor.commit()
    }

    private fun restoreData(key: String) {
        textFromPreferences = preferences!!.getString(key, "")
    }

    private val dataFromHome: Unit
        private get() {
            val tempin: TempIn = TempIn()
            tempin.execute(null, null, null)
            val tempout: TempOut = TempOut()
            tempout.execute(null, null, null)
            val Window: Window = Window()
            Window.execute(null, null, null)
            val Rain: Rain = Rain()
            Rain.execute(null, null, null)
            val moves: Moves = Moves()
            moves.execute(null, null, null)
        }
    val isOnline: Boolean
        get() {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnectedOrConnecting) {
                net = true
                true
            } else {
                net = false
                false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        tempIn = findViewById<View>(R.id.textView1) as TextView
        tempOut = findViewById<View>(R.id.textView4) as TextView
        window = findViewById<View>(R.id.textView7) as TextView
        move = findViewById<View>(R.id.textView8) as TextView
        rain = findViewById<View>(R.id.textView10) as TextView
        time = findViewById<View>(R.id.textView11) as TextView
        button1 = findViewById<View>(R.id.button1) as Button
        restoreData(PREFERENCES_TIME)
        time!!.text = textFromPreferences
        restoreData(PREFERENCES_TEMP_IN)
        tempIn!!.text = "$textFromPreferences \u00B0C"
        restoreData(PREFERENCES_TEMP_OUT)
        tempOut!!.text = "$textFromPreferences \u00B0C"
        //rain**************************************************************
        restoreData(PREFERENCES_RAIN)
        if (textFromPreferences == "YES") {
            rain!!.setText(R.string.yes)
        }
        if (textFromPreferences == "NO") {
            rain!!.setText(R.string.no)
        }

        //window************************************************************
        restoreData(PREFERENCES_WINDOW)
        if (textFromPreferences == "open") {
            window!!.setText(R.string.open)
        }
        if (textFromPreferences == "close") {
            window!!.setText(R.string.close)
        }
        //move**************************************************************
        restoreData(PREFERENCES_MOVE)
        if (textFromPreferences == "1") {
            move!!.setText(R.string.yes)
        }
        if (textFromPreferences == "0") {
            move!!.setText(R.string.no)
        }


        //link***************************************************************
        restoreData(PREFERENCES_LINK)
        link = textFromPreferences
        dataFromHome
        val kliknij = View.OnClickListener {
            isOnline
            if (net) {
                restoreData(PREFERENCES_LINK)
                link = textFromPreferences
                dataFromHome
                if (Srain != null) {
                    val simpleDateHere = SimpleDateFormat("yyyy-MM-dd kk:mm")
                    time!!.text = simpleDateHere.format(Date())
                    saveData(PREFERENCES_TIME, simpleDateHere.format(Date()))
                }
                if (Stempin != null) {
                    tempIn!!.text = "$Stempin \u00B0C"
                    saveData(PREFERENCES_TEMP_IN, Stempin!!)
                }
                if (Stempout != null) {
                    tempOut!!.text = "$Stempout \u00B0C"
                    saveData(PREFERENCES_TEMP_OUT, Stempout!!)
                }
                if (Swindow != null) {
                    if (Swindow == "OPEN") {
                        window!!.setText(R.string.open)
                    }
                    if (Swindow == "CLOSE") {
                        window!!.setText(R.string.close)
                    }
                    saveData(PREFERENCES_WINDOW, Swindow!!)
                }
                if (Smove != null) {
                    if (Imove == 1) {
                        move!!.setText(R.string.yes)
                    }
                    if (Imove == 0) {
                        move!!.setText(R.string.no)
                    }
                    saveData(PREFERENCES_MOVE, Smove!!)
                }
                if (Srain != null) {
                    if (Srain == "YES") {
                        rain!!.setText(R.string.yes)
                    }
                    if (Srain == "NO") {
                        rain!!.setText(R.string.no)
                    }
                    saveData(PREFERENCES_RAIN, Srain!!)
                }
            } else {
                val context = applicationContext
                val text: CharSequence = "Turn on wifi or mobile data"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }
        button1!!.setOnClickListener(kliknij)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu items for use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when (item.itemId) {
            R.id.action_settings -> {
                context = applicationContext
                val intent = Intent(context, Settings::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "myPreferences"
        private const val PREFERENCES_TEMP_IN = "tempin"
        private const val PREFERENCES_TEMP_OUT = "tempout"
        private const val PREFERENCES_WINDOW = "window"
        private const val PREFERENCES_RAIN = "rain"
        private const val PREFERENCES_MOVE = "move"
        private const val PREFERENCES_TIME = "time"
        private const val PREFERENCES_LINK = "link"
    }
}