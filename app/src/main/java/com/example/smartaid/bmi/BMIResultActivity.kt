package com.example.smartaid.bmi

import com.example.smartaid.R
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartaid.history.AppDatabase
import com.example.smartaid.history.HistoryEntity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class BMIResultActivity : AppCompatActivity() {

    private lateinit var bmiAnimation: LottieAnimationView
    private lateinit var bmiValueText: TextView
    private lateinit var bmiTableImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiresult)

        bmiAnimation = findViewById(R.id.bmiAnimation)
        bmiValueText = findViewById(R.id.bmiValueText)
        bmiTableImage = findViewById(R.id.bmiTableImage)



        val bmi = intent.getFloatExtra("BMI_VALUE", -1f)

        if(bmi == -1f){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = AppDatabase.getDatabase(this)
        val historyDao = db.historyDao()

        lifecycleScope.launch{
            historyDao.insert(
                HistoryEntity(
                    type = "BMI",
                    message = "Your BMI is $bmi",
                    timestamp = System.currentTimeMillis()
                )
            )
        }
        bmiValueText.text = "BMI: %.2f".format(bmi)

        when {
            bmi < 18.5 -> {
                bmiAnimation.setAnimation("yellow_caution.json")
            }
            bmi in 18.5..24.9 -> {
                bmiAnimation.setAnimation("green_tick.json")
            }
            else -> {
                bmiAnimation.setAnimation("red_warning.json")
            }
        }

        bmiAnimation.playAnimation()
    }
}