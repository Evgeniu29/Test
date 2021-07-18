package com.genius.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.genius.test.database.UserEntity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


lateinit var text: TextView

var isExist = false

private lateinit  var mAuth: FirebaseAuth

lateinit var enter: Button


var list: ArrayList<UserEntity> = ArrayList()


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser

        GlobalScope.launch {

            delay(2000L)

            if (user == null) {

                val signInIntent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(signInIntent)


            } else {

                val dashboardIntent = Intent(this@MainActivity, DashboardActivity::class.java)
                startActivity(dashboardIntent)

            }

        }.start()
}

    }

