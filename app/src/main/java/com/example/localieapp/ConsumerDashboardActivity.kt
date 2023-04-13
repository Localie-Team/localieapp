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
            val emailStr = email.toString()

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
                            document.data!!.get("product").toString()
                        )
                    )
                }

                for (i in listOfCoupons!!.indices) {
                    listOfCoupons!![i].coordinate = i;
                }

                bundle = Bundle().apply { putParcelableArrayList("coupons", listOfCoupons) }



                navigationView = findViewById(R.id.dashboard_tab_layout)
                val tab = navigationView!!.getTabAt(1)
                tab?.select()

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

                // When we open the application first time
                // the fragment should be shown to the user
                // in this case it is home fragment
                val fragment = ConsumerDealsFragment()
                fragment.arguments = bundle
                val earnFragment = ConsumerEarnFragment()
                earnFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, fragment, "")
                fragmentTransaction.commit()
            }
    }

}