package com.example.task_robofriends.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.task_robofriends.MainActivity
import com.example.task_robofriends.R
import com.example.task_robofriends.databinding.ActivitySignInBinding
import com.example.task_robofriends.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.register.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signIn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.pass.text.toString()
            if(dataValidation()){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else
                        Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dataValidation(): Boolean {
        val email = binding.email.text.toString()
        if(email == ""){
            binding.emailTv.error = "This is required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailTv.error = "Check email format"
            return false
        }
        if(binding.pass.text.toString() == ""){
            binding.passTv.error = "This is required field"
            binding.passTv.errorIconDrawable = null
            return false
        }
        return true
    }
}