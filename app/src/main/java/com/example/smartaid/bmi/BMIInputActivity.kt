package com.example.smartaid.bmi

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

class BMIInputActivity : AppCompatActivity() {

    private lateinit var heightInput: EditText
    private lateinit var weightInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var calculateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiinput)

        heightInput = findViewById(R.id.heightInput)
        weightInput = findViewById(R.id.weightInput)
        genderGroup = findViewById(R.id.genderGroup)
        calculateBtn = findViewById(R.id.calculateBtn)

        calculateBtn.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val heightText = heightInput.text.toString()
        val weightText = weightInput.text.toString()

        if (heightText.isEmpty() || weightText.isEmpty() || genderGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val height = heightText.toFloat() / 100   // Convert cm to meter
        val weight = weightText.toFloat()
        val bmi = weight / (height * height)

        // Sending data to BMIResultActivity
        val intent = Intent(this, BMIResultActivity::class.java)
        intent.putExtra("BMI_VALUE", bmi)
        startActivity(intent)
    }
}