package com.example.localieapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.localieapp.model.Coupon
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class ConsumerDashboardActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var storage: FirebaseStorage? = null
    var firebaseUser: FirebaseUser? = null
    var myuid: String? = null
    var navigationView: TabLayout? = null
    var userEmail: MaterialToolbar? = null
    var userName: MaterialToolbar? = null

    val db = Firebase.firestore;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_dashboard)

        // Firebase Storage init
        storage = Firebase.storage

        firebaseAuth = FirebaseAuth.getInstance()

        var user = firebaseAuth!!.currentUser

        if (user != null)
        {
            val email = user!!.email
            val name = user!!.displayName
            val emailStr = email.toString()
            val nameStr = name.toString()
            userEmail = findViewById(R.id.title_consumer_dashboard)
            userEmail!!.subtitle = emailStr
        }

        var bundle = Bundle();
        var listOfCoupons = ArrayList<Coupon>()
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



                bundle = Bundle().apply { putParcelableArrayList("coupons", listOfCoupons) }
                
                val curFragmentName = intent.getStringExtra("Current_Fragment")

                navigationView = findViewById(R.id.dashboard_tab_layout)

                // When we open the application first time
                // the home fragment should be shown to the user

                // When navigating from the Settings Activity,
                // the profile fragment should be shown

                if(curFragmentName == "Consumer_Profile"){
                    val tab = navigationView!!.getTabAt(0)
                    tab?.select()
                    val fragment = ConsumerProfileFragment()
                    fragment.arguments = bundle
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.content, fragment, "")
                    fragmentTransaction.commit()
                }
                else{
                    val tab = navigationView!!.getTabAt(1)
                    tab?.select()
                    val fragment = ConsumerDealsFragment()
                    fragment.arguments = bundle
                    val earnFragment = ConsumerEarnFragment()
                    earnFragment.arguments = bundle
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.content, fragment, "")
                    fragmentTransaction.commit()
                }

                // handles current tab selection when navigating from settings activity

                navigationView!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

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
                                fragment = ConsumerEarnFragment();
                                fragment.arguments = bundle
                            }
                        }


                        if (fragment != null) {
                            Log.d("TAG", fragment.toString())
                            val fragmentTransaction = supportFragmentManager.beginTransaction()
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

            }
    }

}