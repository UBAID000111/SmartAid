package com.example.smartaid.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.smartaid.MainActivity
import com.example.smartaid.R
import com.example.smartaid.auth.LoginActivity
import com.example.smartaid.bmi.BMIInputActivity
import com.example.smartaid.bp.BPInputActivity
import com.example.smartaid.emergency.EmergencyContactActivity
import com.example.smartaid.heart.HeartRateInputActivity
import com.example.smartaid.history.HistoryActivity
import com.example.smartaid.medicine.MedicineReminderActivity
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var chatbotRedirect: ImageView

    private lateinit var symptomChecker: CardView

    private lateinit var bmiChecker: CardView

    private lateinit var medicineReminder: CardView

    private lateinit var emergencyField: CardView

    private lateinit var heartRateField: CardView

    private lateinit var bpField: CardView

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var navigationView: NavigationView

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        chatbotRedirect = findViewById(R.id.chatbotRedirect)
        symptomChecker = findViewById(R.id.symptomChecker)
        bmiChecker = findViewById(R.id.bmiChecker)
        medicineReminder = findViewById(R.id.medicineRemainder)
        emergencyField = findViewById(R.id.emergency)
        heartRateField = findViewById(R.id.heartField)
        bpField = findViewById(R.id.BPCard)
        drawerLayout = findViewById(R.id.drawerlayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_about -> {

                }
                R.id.nav_contact -> {

                }
                R.id.nav_history -> {

                    val intentHistory = Intent(this, HistoryActivity::class.java)
                    startActivity(intentHistory)
                }
                R.id.nav_logout -> {
                    logoutUser()
                    Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        chatbotRedirect.setOnClickListener {
            val intent3 = Intent(this, MainActivity::class.java)
            startActivity(intent3)
        }

        bmiChecker.setOnClickListener {
            val intent2 = Intent(this, BMIInputActivity::class.java)
            startActivity(intent2)
        }

        symptomChecker.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("symptomMessage", "I am feeling low, I have some symptoms.")
            startActivity(intent)
        }
        medicineReminder.setOnClickListener {
            val intent4 = Intent(this, MedicineReminderActivity::class.java)
            startActivity(intent4)
        }

        heartRateField.setOnClickListener {
            val intent6 = Intent(this, HeartRateInputActivity::class.java)
            startActivity(intent6)
        }


        emergencyField.setOnClickListener {
            val intent5 = Intent(this, EmergencyContactActivity::class.java)
            startActivity(intent5)
        }

        bpField.setOnClickListener {
            val intent7 = Intent(this, BPInputActivity::class.java)
            startActivity(intent7)
        }
    }
    private fun logoutUser() {
        val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
        prefs.edit().clear().apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}