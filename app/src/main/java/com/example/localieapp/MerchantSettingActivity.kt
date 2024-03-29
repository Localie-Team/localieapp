package com.example.localieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.localieapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MerchantSettingActivity : AppCompatActivity() {
    // back button navigates to the dashboard
    private var back: ImageButton? = null
    private var logout: TextView? = null


    private var name: TextView? = null
    private var agerange: TextView? = null
    private var region: TextView? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get database and authorization instances
        val db = Firebase.firestore;
        val mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_merchant_settings)



        // assign buttons to the Views in the layout
        back = findViewById(R.id.settings_to_dashboard_button)
        logout = findViewById(R.id.log_out)
        
        // find user in database and get user information
        db.collection("users").whereEqualTo("UID", mAuth.currentUser!!.uid).get()
            .addOnSuccessListener{ documents ->
                for(document in documents) {
                    user = document.toObject<User>()
                    user!!.email = mAuth.currentUser!!.email.toString()

                }
            }
            .addOnFailureListener {
                // if they dont have anything, just fill with null for now
                user = User("null","null",listOf("null"),listOf("null"), "null","null","null","null","null", "null", "null", "null")
            }
/*
        val pic = user!!.profile_pic.toString()
        val picMer = findViewById<ImageView>(R.id.user_profile_pic)

        val desiredWidth = 800 // Specify the desired width in pixels
        val desiredHeight = 800 // Specify the desired height in pixels

        Glide.with(this@MerchantSettingActivity)
            .load(pic)
            .override(desiredWidth, desiredHeight)
            .centerCrop() // Optional: Apply center crop if needed
            .into(picMer)*/

        back?.setOnClickListener(View.OnClickListener {
        val mainIntent = Intent(this@MerchantSettingActivity, MerchantDashboardActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        // using putExtra, we can specify which fragment in the Dashboard Activity to navigate to
        mainIntent.putExtra("Current_Fragment", "Merchant_Profile")
        startActivity(mainIntent)
        })

        // logs out user, navigates to login activity
        logout?.setOnClickListener {
            startActivity(Intent(this@MerchantSettingActivity, LoginActivity::class.java))
        }


    }

    companion object {
        private const val ARG_PARAM1 = "param1"
    }

}