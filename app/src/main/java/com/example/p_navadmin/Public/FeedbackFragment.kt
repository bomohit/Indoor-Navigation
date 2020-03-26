package com.example.p_navadmin.Public


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import com.example.p_navadmin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        rating_bar.setOnRatingBarChangeListener { ratingBar, _, _ ->

            // 
        }

        return root
    }


}
