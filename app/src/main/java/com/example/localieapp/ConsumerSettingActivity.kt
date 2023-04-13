package com.example.localieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.localieapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConsumerSettingActivity : AppCompatActivity() {
    private var back: ImageButton? = null
    private var logout: TextView? = null

    private var name: TextView? = null
    private var agerange: TextView? = null
    private var region: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore;
        val mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_consumer_settings)
        back = findViewById(R.id.settings_to_dashboard_button)
        logout = findViewById(R.id.log_out)
        // this gets the users opt in information that they give us
        db.collection("users").whereEqualTo("UID", mAuth.currentUser!!.uid).get()
            .addOnSuccessListener{ documents ->
                for(document in documents) {
                    var user = User(document.data!!.get("name").toString(),
                        document.data.get("last").toString(),
                        document.data.get("cart") as Array<String>,
                        document.data.get("win") as Array<String>,
                        document.data.get("age").toString(),
                        mAuth.currentUser!!.email.toString(),
                        "null",
                        mAuth.currentUser!!.uid
                    )
                }
            }
            .addOnFailureListener {
                // if they dont have anything, just fill with null for now
                var user = User("null","null",arrayOf("null"),arrayOf("null"), "null","null","null","null")
            }

        back?.setOnClickListener(View.OnClickListener {
        val mainIntent = Intent(this@ConsumerSettingActivity, ConsumerDashboardActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        })

        logout?.setOnClickListener {
            startActivity(Intent(this@ConsumerSettingActivity, LoginActivity::class.java))
        }

    }

}