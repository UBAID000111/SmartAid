package com.example.smartaid.emergency

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.smartaid.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class EmergencyContactActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var contact1: EditText
    private lateinit var contact2: EditText
    private lateinit var contact3: EditText
    private lateinit var btnSave: Button
    private lateinit var btnSend: Button

    private lateinit var hospitalNearby: Button

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_chat)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        contact1 = findViewById(R.id.etContact1)
        contact2 = findViewById(R.id.etContact2)
        contact3 = findViewById(R.id.etContact3)
        btnSave = findViewById(R.id.btnSave)
        btnSend = findViewById(R.id.btnSend)
        hospitalNearby = findViewById(R.id.hospitalNearby)

        loadSavedContacts()

        btnSave.setOnClickListener {
            saveContacts()
        }

        btnSend.setOnClickListener {
            checkLocationPermission()
        }

        hospitalNearby.setOnClickListener {
            openNearbyHospitals()
        }
    }

    private fun saveContacts() {
        val prefs = getSharedPreferences("EmergencyContacts", MODE_PRIVATE)
        prefs.edit().apply {
            putString("contact1", contact1.text.toString())
            putString("contact2", contact2.text.toString())
            putString("contact3", contact3.text.toString())
            apply()
        }
        Toast.makeText(this, "Contacts saved!", Toast.LENGTH_SHORT).show()
    }

    private fun loadSavedContacts() {
        val prefs = getSharedPreferences("EmergencyContacts", MODE_PRIVATE)
        contact1.setText(prefs.getString("contact1", ""))
        contact2.setText(prefs.getString("contact2", ""))
        contact3.setText(prefs.getString("contact3", ""))
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            sendEmergencyMessage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendEmergencyMessage()
            } else {
                Toast.makeText(this, "Location permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmergencyMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location permission not granted!", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val message =
                    "🚨 I am in danger!\nMy location: https://www.google.com/maps?q=${location.latitude},${location.longitude}"

                val contacts = listOf(
                    contact1.text.toString(),
                    contact2.text.toString(),
                    contact3.text.toString()
                ).filter { it.isNotEmpty() }

                if (contacts.isEmpty()) {
                    Toast.makeText(this, "No contacts saved!", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                for (contact in contacts) {
                    sendViaWhatsApp(contact, message)
                }
            } else {
                Toast.makeText(this, "Unable to fetch location!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendViaWhatsApp(number: String, message: String) {
        try {
            val url = "https://api.whatsapp.com/send?phone=$number&text=${Uri.encode(message)}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp not installed!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openNearbyHospitals() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val gmmIntentUri = if (location != null) {
                Uri.parse("geo:${location.latitude},${location.longitude}?q=hospitals")
            } else {
                Uri.parse("geo:0,0?q=hospitals")
            }

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            try {
                startActivity(Intent.createChooser(mapIntent, "Choose Maps App"))
            } catch (e: Exception) {
                Toast.makeText(this, "No app found to open maps!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}