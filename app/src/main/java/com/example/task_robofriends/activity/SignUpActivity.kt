package com.example.task_robofriends.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.task_robofriends.R
import com.example.task_robofriends.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.login.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUp.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.pass.text.toString()
            if(dataValidation()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
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
        if(binding.pass.length() < 6){
            binding.passTv.error = "Password should at least be 6 characters long"
            binding.passTv.errorIconDrawable = null
            return false
        }
        if(binding.confirmPass.text.toString() == ""){
            binding.confirmPassTv.error = "This is required field"
            binding.confirmPassTv.errorIconDrawable = null
            return false
        }
        if(binding.pass.text.toString() != binding.confirmPass.text.toString()) {
            binding.passTv.error = "Password do not match"
            return false
        }
        return true
    }
}