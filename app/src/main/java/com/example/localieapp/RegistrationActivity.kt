package com.example.localieapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistrationActivity : AppCompatActivity() {
    private var email: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var name: TextInputEditText? = null
    private var mRegister: Button? = null
    private var existaccount: TextView? = null
    private var browseguest: TextView? = null
    private var progressDialog: ProgressDialog? = null
    private var mAuth: FirebaseAuth? = null

    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        email = findViewById(R.id.register_email)
        name = findViewById(R.id.register_name)
        password = findViewById(R.id.register_password)
        mRegister = findViewById(R.id.register_button) as Button
        existaccount = findViewById(R.id.homepage)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Register")



        mRegister?.setOnClickListener(View.OnClickListener {
            val emaill = email?.getText().toString().trim { it <= ' ' }
            val uname = name?.getText().toString().trim { it <= ' ' }
            val pass = password?.getText().toString().trim { it <= ' ' }
            if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
                email?.setError("Invalid Email")
                email?.setFocusable(true)
            } else if (pass.length < 6) {
                password?.setError("Length Must be greater than 6 character")
                password?.setFocusable(true)
            } else {
                registerUser(emaill, pass, uname)
            }
        })
        existaccount?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@RegistrationActivity,
                    LoginActivity::class.java
                )
            )
        })
    }

    private fun registerUser(emaill: String, pass: String, uname: String) {
        progressDialog!!.show()
        //logs in the user while uploading to the database
        mAuth!!.createUserWithEmailAndPassword(emaill, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressDialog!!.dismiss()
                val user = mAuth!!.currentUser
                val email = user!!.email
                val uid = user.uid
                val hashMap = HashMap<Any, Any?>()
                hashMap["UID"] = uid
                hashMap["name"] = uname
                hashMap["cart"] = emptyList<String>()
                hashMap["win"] = emptyList<String>()
                db.collection("users")
                    .add(hashMap)
                    .addOnSuccessListener{
                        Toast.makeText(
                    this@RegistrationActivity,
                        "Registered User " + user.email,
                            Toast.LENGTH_LONG
                        ).show()

                        val mainIntent = Intent(this@RegistrationActivity, ConsumerDashboardActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(mainIntent)
                        finish()
                    }
                    .addOnFailureListener {  Toast.makeText(
                        this@RegistrationActivity,
                        "Error",
                        Toast.LENGTH_LONG
                    ).show()}
            } else {
                progressDialog!!.dismiss()
                Toast.makeText(this@RegistrationActivity, "Error", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            progressDialog!!.dismiss()
            Toast.makeText(this@RegistrationActivity, "Error Occurred", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}