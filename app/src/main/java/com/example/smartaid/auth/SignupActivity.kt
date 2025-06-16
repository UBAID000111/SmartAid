package com.example.smartaid.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartaid.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.smartaid.R
import com.example.smartaid.databinding.ActivitySignupBinding
class SignupActivity : AppCompatActivity() {
    private lateinit var signupButton: Button
    private lateinit var binding: ActivitySignupBinding

    private lateinit var loginRedirect: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        loginRedirect = findViewById(R.id.loginRedirect)

        signupButton = findViewById(R.id.signupButton)

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val pass = binding.signupPassword.text.toString()
            auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                Toast.makeText(this, "Signup Successful",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}