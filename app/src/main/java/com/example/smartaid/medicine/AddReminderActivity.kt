package com.example.smartaid.medicine

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.example.smartaid.R
import java.util.Locale

class AddReminderActivity : AppCompatActivity() {

    private lateinit var etTag: EditText
    private lateinit var btnPickTime: Button
    private lateinit var tvTimePicked: TextView
    private lateinit var btnSaveReminder: Button

    private var pickedHour = -1
    private var pickedMinute = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        // Linking IDs properly
        etTag = findViewById(R.id.etTag)
        btnPickTime = findViewById(R.id.btnPickTime)
        tvTimePicked = findViewById(R.id.tvTimePicked)
        btnSaveReminder = findViewById(R.id.btnSaveReminder)

        // Time Picker Dialog
        btnPickTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                pickedHour = selectedHour
                pickedMinute = selectedMinute
                tvTimePicked.text = String.format("%02d:%02d", pickedHour, pickedMinute)
            }, hour, minute, true)

            timePickerDialog.show()
        }

        // Save Reminder
        btnSaveReminder.setOnClickListener {
            val tag = etTag.text.toString().trim()
            if (tag.isEmpty() || pickedHour == -1) {
                Toast.makeText(this, "Please enter tag and pick time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send back data to MedicineReminderActivity
            val intent = Intent()
            intent.putExtra("tag", tag)
            intent.putExtra("hour", pickedHour)
            intent.putExtra("minute", pickedMinute)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}