package com.example.smartaid.bp

import com.example.smartaid.R
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smartaid.history.AppDatabase
import com.example.smartaid.history.HistoryEntity
import kotlinx.coroutines.launch

class BPOutputActivity : AppCompatActivity() {

    private lateinit var BPAnimation: LottieAnimationView
    private lateinit var BPValueText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bpoutput)

        BPAnimation = findViewById(R.id.BPanimation)
        BPValueText = findViewById(R.id.BPValueText)

        val sysBP = intent.getFloatExtra("BP Systolic Value", -1f)
        val diasBP = intent.getFloatExtra("BP Diastolic Value", -1f)

        val db = AppDatabase.getDatabase(this)
        val historyDao = db.historyDao()

        lifecycleScope.launch{
            historyDao.insert(
                HistoryEntity(
                    type = "BP",
                    message = "Your BP is $sysBP/$diasBP",
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        if(sysBP == -1f || diasBP == -1f){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        BPValueText.text = "Systolic: $sysBP mmHg \n Diastolic: $diasBP mmHg"

        if (sysBP in 70.0..90.0 && diasBP in 50.0..60.00) {
            BPAnimation.setAnimation("yellow_caution.json")
            Toast.makeText(this, " Your Bp is low! Go check Doctor", Toast.LENGTH_LONG).show()
        } else if(sysBP in 90.0..125.0 && diasBP in 60.0..85.0) {
            BPAnimation.setAnimation("healthy_heart.json")
            Toast.makeText(this, "You are perfect!", Toast.LENGTH_LONG).show()
        } else {
            BPAnimation.setAnimation("red_warning.json")
            Toast.makeText(this, " Your Bp is high! Go check Doctor", Toast.LENGTH_LONG).show()
        }

        BPAnimation.playAnimation()
    }
}