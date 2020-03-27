package com.example.p_navadmin.Public


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import com.example.p_navadmin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FeedbackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_feedback, container, false)

        val db = Firebase.firestore

        val rating_bar = root.findViewById<RatingBar>(R.id.ratingBar)

        //check if the user already give rating or not
        val filename = "myfile"
        var fileR = File(context?.filesDir,filename)
        var fileExists = fileR.exists()
        var user = ""


        if (fileExists) {
            d("faris", "fileExist")
            // if file exist , read from the file
            val uContent = context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
                lines.fold("") { some, text ->
                    "$some$text"
                }
            }

            d("faris", "$uContent")
            // set to the user, so that it can detect weather user exist or not
            user = uContent.toString()

            db.collection("Rating").document("$uContent")
                .get()
                .addOnSuccessListener { result ->
                    d("faris", "rate is ${result.getField<Float>("rate")}")
                    val rate = result.getField<Float>("rate")
                    rating_bar.rating = rate!!.toFloat()
                }

        }
        else {
            d("faris", "file not Exist")
        }



        rating_bar.setOnRatingBarChangeListener { ratingBar, _, _ ->

            // get the rating given
            val rateGiven = ratingBar.rating
            val rateInfo = hashMapOf(
                "rate" to rateGiven
            )

            if (user.isEmpty()) {
                // send to fireStore
                db.collection("Rating")
                    .add(rateInfo)
                    .addOnSuccessListener { result ->
                        d("faris", "rate uploaded rate : $rateGiven")

                        // if user not exist yet
                        if (user.isEmpty()) {
                            val uid = result.id
                            context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
                                it?.write(uid.toByteArray())
                            }
                            d("faris", "user added")
                        }

                    }
                    .addOnFailureListener { e ->
                        d("faris", "error rating ", e)
                    }
            }
            else {
                // send to fireStore
                db.collection("Rating").document("$user")
                    .set(rateInfo)
                    .addOnSuccessListener { result ->
                        d("faris", "rate uploaded rate : $rateGiven")

                    }
                    .addOnFailureListener { e ->
                        d("faris", "error rating ", e)
                    }
            }
        }

        return root
    }


}
