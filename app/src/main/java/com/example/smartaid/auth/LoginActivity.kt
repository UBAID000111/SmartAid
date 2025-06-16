package com.example.smartaid.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smartaid.MainActivity
import com.example.smartaid.R
import com.example.smartaid.databinding.ActivityLoginBinding
import com.example.smartaid.databinding.ActivitySignupBinding
import com.example.smartaid.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var binding: ActivityLoginBinding

    private lateinit var signupRedirect: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        signupRedirect = findViewById(R.id.signupRedirect)

        loginButton = findViewById(R.id.loginButton)

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val pass = binding.loginPassword.text.toString()
            auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        signupRedirect.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}

