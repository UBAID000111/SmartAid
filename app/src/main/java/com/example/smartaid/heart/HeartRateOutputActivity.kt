package com.example.smartaid.heart

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

class HeartRateOutputActivity : AppCompatActivity() {

    private lateinit var heartAnimation: LottieAnimationView
    private lateinit var heartValueText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate_output)

        heartAnimation = findViewById(R.id.hranimation)
        heartValueText= findViewById(R.id.hrValueText)

        val hr = intent.getFloatExtra("Heart rate Value", -1f)

        val db = AppDatabase.getDatabase(this)
        val historyDao = db.historyDao()

        lifecycleScope.launch{
            historyDao.insert(
                HistoryEntity(
                    type = "HR",
                    message = "Your Heart rate is $hr",
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        if(hr == -1f){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        heartValueText.text = "Heart Rate: %.2f".format(hr)

        when {
            hr < 70 -> {
                heartAnimation.setAnimation("yellow_caution.json")
                Toast.makeText(this, "Your heart is slow! Go Check Doctor", Toast.LENGTH_LONG).show()
            }
            hr in 71.0..79.9 -> {
                heartAnimation.setAnimation("healthy_heart.json")
                Toast.makeText(this, "You are perfect!", Toast.LENGTH_LONG).show()
            }

            hr in 80.0..83.0 -> {
                heartAnimation.setAnimation("green_tick.json")
                Toast.makeText(this, "Your heart is average! Control on Junk", Toast.LENGTH_LONG).show()
            }
            else -> {
                heartAnimation.setAnimation("red_warning.json")
                Toast.makeText(this, "Your heart is fast! Go Check Doctor", Toast.LENGTH_LONG).show()
            }
        }

        heartAnimation.playAnimation()
    }
}