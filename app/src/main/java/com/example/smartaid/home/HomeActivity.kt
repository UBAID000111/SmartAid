package com.example.smartaid.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartaid.MainActivity
import com.example.smartaid.R
import com.example.smartaid.bmi.BMIInputActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var chatbotRedirect: ImageView

    private lateinit var symptomChecker: CardView

    private lateinit var bmiChecker: CardView

    private lateinit var medicineReminder: CardView

    private lateinit var emergencyField: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        chatbotRedirect = findViewById(R.id.chatbotRedirect)
        symptomChecker = findViewById(R.id.symptomChecker)
        bmiChecker = findViewById(R.id.bmiChecker)
        medicineReminder = findViewById(R.id.medicineRemainder)
        emergencyField = findViewById(R.id.emergency)

        chatbotRedirect.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bmiChecker.setOnClickListener {
            val intent2 = Intent(this, BMIInputActivity::class.java)
            startActivity(intent2)
        }
    }
}