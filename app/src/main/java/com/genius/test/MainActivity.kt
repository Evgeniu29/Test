package com.genius.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.genius.test.LoginViewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

private lateinit  var mAuth: FirebaseAuth

var isExist = false

lateinit var loginViewModel: LoginViewModel



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        user!!.displayName?.let { loginViewModel.getLoginDetails(this, it) }!!.observe(this, Observer {

            if (it == null) {
                Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_LONG).show()
            }
        })

            /**If user is not authenticated, send him to SignInActivity to authenticate first.
             * Else send him to DashboardActivity*/

            /**If user is not authenticated, send him to SignInActivity to authenticate first.
             * Else send him to DashboardActivity*/
            Handler().postDelayed({
                if (user != null) {

                    val dashboardIntent = Intent(this, DashboardActivity::class.java)
                    startActivity(dashboardIntent)
                    finish()

                } else {
                    val signInIntent = Intent(this, SignInActivity::class.java)
                    startActivity(signInIntent)
                    finish()
                }
            }, 2000)

        }}
