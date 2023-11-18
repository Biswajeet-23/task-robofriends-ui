package com.example.task_robofriends

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task_robofriends.activity.SignInActivity
import com.example.task_robofriends.adapter.UserListAdapter
import com.example.task_robofriends.api.RetrofitInstance
import com.example.task_robofriends.databinding.ActivityMainBinding
import com.example.task_robofriends.model.Users
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var layout: LinearLayoutManager
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = binding.recyclerView
        layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if(isNetworkConnected()){
            retrieveData()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(R.string.ok) { _, _ -> }
                .setIcon(R.drawable.baseline_assignment_late_24).show()
        }

    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun retrieveData() {

        //Handle exceptions if any
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(R.string.ok) { _, _ -> }
                .setIcon(R.drawable.baseline_assignment_late_24).show()
        }
        //get data from response and  add to list
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val users = RetrofitInstance().getDetails()
            binding.recyclerView.adapter = UserListAdapter(this@MainActivity, users)
            recyclerView.layoutManager = layout
        }
    }
}