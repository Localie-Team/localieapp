package com.example.localieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.localieapp.model.Coupon
import com.example.localieapp.model.User
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*

class ConsumerDashboardActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var storage: FirebaseStorage? = null
    var firebaseUser: FirebaseUser? = null
    var myuid: String? = null
    var navigationView: TabLayout? = null
    var userEmail: MaterialToolbar? = null
    var userName: MaterialToolbar? = null
    var user: User? = null
    var userRef: String? = null
    var userUID: String? = null

    val db = Firebase.firestore;



    suspend fun failedConcurrentSum() {
        runBlocking {

            Log.d("firebaseAuth", "Guest Login Should Be Here")
            try {
                userEmail!!.subtitle = firebaseAuth!!.currentUser!!.email.toString()
            } catch (e: Exception) {

                Log.d("firebaseAuth fail", "Guest Login Should Be Here")

                firebaseAuth!!.signInWithEmailAndPassword("guest@gmail.com", "guest123")
                //TODO: SETUP: Limited functionality for guest account. Actually you shouldnt have to login a user at all.

                        Log.d("Login:", "aakdhsksadhs")

                delay(3000) //TODO: Any other way to fix concurrency? Lock system maybe? Synchronize?
                userEmail!!.subtitle = firebaseAuth!!.currentUser!!.email.toString()
            }

            Log.d("testing", "something")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) = runBlocking<Unit> {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_dashboard)

        // Firebase Storage init
        storage = Firebase.storage

        firebaseAuth = FirebaseAuth.getInstance()

        userEmail = findViewById(R.id.title_consumer_dashboard)


        failedConcurrentSum()


        try {
            val user = Firebase.auth.currentUser
            if (user != null) {
                Log.d("auth_user", user!!.uid)
            } else {
                Log.d("auth_user", "null")
                userUID = firebaseAuth!!.uid
                Log.d("auth_user_Uid", userUID.toString())
            }
//            Log.d("auth_user", user!!.uid)
        } catch (e: java.lang.Exception) {
            Log.e("MYAPP", "exception", e)
        }
        var bundle = Bundle();
        var listOfCoupons = ArrayList<Coupon>()
        // find user in database and get user information
        db.collection("users").whereEqualTo("UID", firebaseAuth!!.currentUser!!.uid).get()
            .addOnSuccessListener { Udocuments ->
                Log.d("found UID", Udocuments.toString())
                for (Udocument in Udocuments) {
                    user = Udocument.toObject<User>()
                    userRef = Udocument.reference.id
                    user!!.email = firebaseAuth!!.currentUser!!.email.toString()
                    ShoppingBag.list_of_coupons = user?.cart as MutableList<String>
                }
                db.collection("coupons").get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {

                            listOfCoupons.add(
                                Coupon(
                                    0,
                                    document.data!!.get("url").toString(),
                                    document.data!!.get("product").toString(),
                                    document.data!!.get("date_issued").toString(),
                                    document.data!!.get("value").toString(),
                                    document.data!!.get("vendor").toString(),
                                    document.id
                                )
                            )
                            Log.d("docId", document.id)
                        }

                        for (i in listOfCoupons!!.indices) {
                            listOfCoupons!![i].coordinate = i;
                        }


                        bundle = Bundle().apply {
                            putParcelableArrayList("coupons", listOfCoupons)
                            putParcelable("user", user)
                            putString("userRef", userRef)}

                        val curFragmentName = intent.getStringExtra("Current_Fragment")

                        navigationView = findViewById(R.id.dashboard_tab_layout)

                        // When we open the application first time
                        // the home fragment should be shown to the user

                        // When navigating from the Settings Activity,
                        // the profile fragment should be shown

                        if (curFragmentName == "Consumer_Profile") {
                            val tab = navigationView!!.getTabAt(0)
                            tab?.select()
                            val fragment = ConsumerProfileFragment()
                            fragment.arguments = bundle
                            val fragmentTransaction = supportFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.content, fragment, "")
                            fragmentTransaction.commit()
                        } else {
                            val tab = navigationView!!.getTabAt(1)
                            tab?.select()
                            val fragment = ConsumerDealsFragment()
                            fragment.arguments = bundle
                            val earnFragment = ConsumerEarnFragment1()
                            earnFragment.arguments = bundle
                            val fragmentTransaction = supportFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.content, fragment, "")
                            fragmentTransaction.commit()
                        }

                        // handles current tab selection when navigating from settings activity

                        navigationView!!.addOnTabSelectedListener(object :
                            TabLayout.OnTabSelectedListener {

                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                var fragment: Fragment? = null

                                when (tab!!.position) {
                                    0 -> {
                                        fragment = ConsumerProfileFragment();
                                        fragment.arguments = bundle
                                    }
                                    1 -> {
                                        fragment = ConsumerDealsFragment();
                                        fragment.arguments = bundle
                                    }
                                    2 -> {
                                        fragment = ConsumerEarnFragment1();
                                        fragment.arguments = bundle
                                    }
                                }


                                if (fragment != null) {
                                    Log.d("TAG", fragment.toString())
                                    val fragmentTransaction =
                                        supportFragmentManager.beginTransaction()
                                    fragmentTransaction.replace(R.id.content, fragment, "")
                                    fragmentTransaction.commit()
                                }

                                // Handle tab select
                            }

                            override fun onTabReselected(tab: TabLayout.Tab?) {
                                // Handle tab reselect
                            }

                            override fun onTabUnselected(tab: TabLayout.Tab?) {
                                // Handle tab unselect
                            }
                        })

//                    }
            }
    }

}}
