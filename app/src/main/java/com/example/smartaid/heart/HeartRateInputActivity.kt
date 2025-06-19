package com.example.smartaid.heart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartaid.R
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import com.example.smartaid.bmi.BMIResultActivity

class HeartRateInputActivity : AppCompatActivity() {

    private lateinit var heartRate1: EditText

    private lateinit var heartRate2: EditText

    private lateinit var heartRate3: EditText
    private lateinit var calculateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate_input)

        heartRate1 = findViewById(R.id.heartRate1)
        heartRate2 = findViewById(R.id.heartRate2)
        heartRate3 = findViewById(R.id.heartRate3)
        calculateBtn = findViewById(R.id.calculateBtn)

        calculateBtn.setOnClickListener {
            calculateHR()
        }
    }

    private fun calculateHR() {
        val hr1 = heartRate1.text.toString()
        val hr2 = heartRate2.text.toString()
        val hr3 = heartRate3.text.toString()

        if (hr1.isEmpty() || hr2.isEmpty() || hr3.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val heartRate_1 = hr1.toFloat()
        val heartRate_2 = hr2.toFloat()
        val heartRate_3 = hr3.toFloat()
        val hrCount = (( heartRate_1 + heartRate_2 + heartRate_3 ) / 3)

        val intent = Intent(this, HeartRateOutputActivity::class.java)
        intent.putExtra("Heart rate Value", hrCount)
        startActivity(intent)
    }
}