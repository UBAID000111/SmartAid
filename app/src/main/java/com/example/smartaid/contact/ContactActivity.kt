package com.example.smartaid.contact

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.smartaid.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ContactActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var chipGroup: ChipGroup
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        messageEditText = findViewById(R.id.messageEditText)
        chipGroup = findViewById(R.id.chipGroup)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val message = messageEditText.text.toString()

            val selectedChipId = chipGroup.checkedChipId
            val selectedChipText = if (selectedChipId != -1) {
                findViewById<Chip>(selectedChipId).text.toString()
            } else {
                "Not selected"
            }

            Toast.makeText(
                this,
                "Name: $name\nEmail: $email\nMessage: $message\nType: $selectedChipText",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}