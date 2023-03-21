package com.example.localieapp
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SplashScreen : AppCompatActivity() {
    var currentUser: FirebaseUser? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val db = Firebase.firestore;
        mAuth = FirebaseAuth.getInstance()
        if (mAuth != null) {
            currentUser = mAuth!!.currentUser
        }
        Handler().postDelayed({
            val user = mAuth!!.currentUser
            if (user == null) {
                val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                db.collection("permissions").whereEqualTo("UID", user.uid).get()
                    .addOnSuccessListener { permission ->
                        Log.d("Before:", permission.toString())
                        for(P in permission){
                            Log.d("Before:", P.data!!.toString())
                            if( P.data!!.get("permissions").toString() == "Merchant"){
                                Log.d("Merchant:", "right here!")
                                val mainIntent = Intent(this@SplashScreen, MerchantDashboardActivity::class.java)
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(mainIntent)
                                finish()

                            }else{
                                Log.d("Consumer:", "right here!")
                                val mainIntent = Intent(this@SplashScreen, ConsumerDashboardActivity::class.java)
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(mainIntent)
                                finish()
                            } }
                        if(permission.size() == 0){
                            Log.d("Consumer:", "right here!")
                            val mainIntent = Intent(this@SplashScreen, ConsumerDashboardActivity::class.java)
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(mainIntent)
                            finish()
                        }
                    }
                    .addOnFailureListener{
                        Log.d("Consumer:", "right here!")
                        val mainIntent = Intent(this@SplashScreen, ConsumerDashboardActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(mainIntent)
                        finish()

                    }
            }
        }, 1200)
    }
}
