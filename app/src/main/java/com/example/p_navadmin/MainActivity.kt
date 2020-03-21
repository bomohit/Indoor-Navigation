package com.example.p_navadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        textView.setText("WElcome")

        button.setOnClickListener {

            // continue if username and password are not empty
            if (inputUsername.text.isNotEmpty() && inputPassword.text.isNotEmpty()) {

                db.collection("Login").document("admin")
                    .get()
                    .addOnSuccessListener { result ->
                        d("firebase", "username is : ${result.getField<String>("username")}")
                        d("firebase", "password is : ${result.getField<String>("username")}")
                        val username = result.getField<String>("username")
                        val password = result.getField<String>("password")
                        val iUsername = inputUsername.text.toString()
                        val iPassword = inputPassword.text.toString()

                        if (username.equals(iUsername) && password.equals(iPassword)) {
                            textView.setText(iUsername)
                            startActivity(Intent(this, Admin_Home::class.java))
                        }
                        else {
                            textView.setText("Username or Password is wrong!")
                        }

                    }
                    .addOnFailureListener { e ->
                        d("firebase", "error", e)
                    }

            }

        }
    }
}
