package com.example.p_navadmin.Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import com.example.p_navadmin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.confirmation.*

class AdminConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation)
        d("firebase", "screen open")

        val store = intent.getStringExtra("store")

        // give accesss firestore
        val db = Firebase.firestore

        db.collection("Confirmation Event").document("$store")
            .get()
            .addOnSuccessListener { result ->
                d("firebase", "field is : ${result.getField<String>("description")}")
                val description = result.getField<String>("description")
                val start = result.getField<String>("start")
                val end = result.getField<String>("end")
                val title = result.getField<String>("title")

                confirmationTitle.text = title
                confirmationStart.text = start
                confirmationEnd.text = end
                confirmationDescription.text = description
            }

        confirmFloat.setOnClickListener {
            val title = confirmationTitle.text.toString()
            val start = confirmationStart.text.toString()
            val end = confirmationEnd.text.toString()
            val description = confirmationDescription.text.toString()

            if (title.isNotEmpty() && start.isNotEmpty() && end.isNotEmpty() && description.isNotEmpty()) {
                // send to the event db
                val event = hashMapOf(
                    "title" to "$title",
                    "start" to "$start",
                    "end" to "$end",
                    "description" to "$description"
                )

                db.collection("Event").document("$store")
                    .set(event)
                    .addOnSuccessListener { result ->
                        d("firebase", "updated")
                    }
                    .addOnFailureListener { e ->
                        d("firebase", "error ", e)
                    }

                // delete
                db.collection("Confirmation Event").document("$store")
                    .delete()
                    .addOnSuccessListener { result ->
                        d("firebase", "deleted")
                        startActivity(Intent(this, Admin_Home::class.java))
                    }
                    .addOnFailureListener { e ->
                        d("firebase", "error ", e)
                    }
            }
        }

        rejectFloat.setOnClickListener {
            // delete
            db.collection("Confirmation Event").document("$store")
                .delete()
                .addOnSuccessListener { result ->
                    d("firebase", "deleted")

                    startActivity(Intent(this, Admin_Home::class.java))

                }
                .addOnFailureListener { e ->
                    d("firebase", "error ", e)
                }

        }
    }
}