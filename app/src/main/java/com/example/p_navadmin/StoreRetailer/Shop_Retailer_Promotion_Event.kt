package com.example.p_navadmin.StoreRetailer

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import com.example.p_navadmin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.shop_retailer_promotion.*

class Shop_Retailer_Promotion_Event : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_retailer_promotion)

        val db = Firebase.firestore

        val store = intent.getStringExtra("store")
        d("firebase","$store")

        submitEvent.setOnClickListener {
            d("firebase", "just click")

            // get the input
            val inputTitle = inputTitle.text.toString()
            val inputStart = inputStart.text.toString()
            val inputEnd = inputEnd.text.toString()
            val inputDescription = inputDescription.text.toString()

            // proceed if the text input is not empty
            if (inputTitle.isNotEmpty() && inputStart.isNotEmpty() && inputEnd.isNotEmpty() && inputDescription.isNotEmpty()) {

                // set the data
                val upEvent = hashMapOf(
                    "title" to "$inputTitle",
                    "start" to "$inputStart",
                    "end" to "$inputEnd",
                    "description" to "$inputDescription"
                )

                // send to db
                db.collection("Confirmation Event").document("$store")
                    .set(upEvent)
                    .addOnSuccessListener { result ->
                        d("firebase", "Successfully add new data")
                    }
                    .addOnFailureListener { e ->
                        d("firebase", "error ", e)
                    }
            }

        }
    }
}