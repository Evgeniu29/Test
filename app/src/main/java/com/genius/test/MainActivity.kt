package com.genius.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.genius.test.database.User
import com.genius.test.database.UserDB

import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.CoroutineScope

  var users: List<User>?  =null

 var singleUser: User? = null;

lateinit var text: TextView

var isExist = false

private lateinit  var mAuth: FirebaseAuth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser

        text = findViewById(R.id.text)


            /**If user is not authenticated, send him to SignInActivity to authenticate first.
             * Else send him to DashboardActivity*/

            Handler().postDelayed({
                if (user == null ) {


                    val signInIntent = Intent(this, SignInActivity::class.java)
                    startActivity(signInIntent)
                    finish()

                }

                    else {

                    val dashboardIntent = Intent(this, DashboardActivity::class.java)
                    startActivity(dashboardIntent)
                    finish()}


            }, 5000)

        }

    private fun launch(block: suspend CoroutineScope.() -> Unit) {

    }
}
