package com.example.smartaid.bp

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
import android.widget.NumberPicker
import android.widget.SeekBar
import com.example.smartaid.bmi.BMIResultActivity

class BPInputActivity : AppCompatActivity() {

    private lateinit var bpSys1: EditText

    private lateinit var bpDias1: EditText

    private lateinit var bpSys2: EditText

    private lateinit var bpDias2: EditText

    private lateinit var bpSys3: EditText

    private lateinit var bpDias3: EditText
    private lateinit var calculateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bpinput)

        bpSys1 = findViewById(R.id.bpSys1)
        bpDias1 = findViewById(R.id.bpDias1)
        bpSys2 = findViewById(R.id.bpSys2)
        bpDias2 = findViewById(R.id.bpDias2)
        bpSys3 = findViewById(R.id.bpSys3)
        bpDias3 = findViewById(R.id.bpDias3)


        calculateBtn = findViewById(R.id.calculateBtn)

        calculateBtn.setOnClickListener {
            calculateBP()
        }
    }

    private fun calculateBP() {
        val bpsys1 = bpSys1.text.toString()
        val bpdias1 = bpDias1.text.toString()
        val bpsys2 = bpSys2.text.toString()
        val bpdias2 = bpDias2.text.toString()
        val bpsys3 = bpSys3.text.toString()
        val bpdias3 = bpDias3.text.toString()



        if (bpsys1.isEmpty() || bpdias1.isEmpty() || bpsys2.isEmpty() || bpdias2.isEmpty() || bpsys3.isEmpty() || bpdias3.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val sys_1 = bpsys1.toFloat()
        val dias_1 = bpdias1.toFloat()
        val sys_2 = bpsys2.toFloat()
        val dias_2 = bpdias2.toFloat()
        val sys_3 = bpsys3.toFloat()
        val dias_3 = bpdias3.toFloat()
        val hrSys = (( sys_1 + sys_2 + sys_3 ) / 3)
        val hrDias = ((dias_1 + dias_2 + dias_3) / 3)

        val intent = Intent(this, BPOutputActivity::class.java)
        intent.putExtra("BP Systolic Value", hrSys)
        intent.putExtra("BP Diastolic Value", hrDias)
        startActivity(intent)
    }
}