package com.example.localieapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.localieapp.model.Coupon
import com.example.localieapp.model.User
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.bumptech.glide.Glide

class MerchantDashboardActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var storage: FirebaseStorage? = null
    var firebaseUser: FirebaseUser? = null
    var myuid: String? = null
    var navigationView: TabLayout? = null
    val mAuth = FirebaseAuth.getInstance()
    var userEmail: MaterialToolbar? = null
    var userName: MaterialToolbar? = null
    var user: User? = null

    val db = Firebase.firestore;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merchant_dashboard)

        storage = Firebase.storage



        // Firebase Storage init
        storage = Firebase.storage

        firebaseAuth = FirebaseAuth.getInstance()

        //Log.d("user EXIST?", user.toString())



        var bundle = Bundle()
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
                            document.data!!.get("vendor").toString()
                        )
                    )
                }

                // find user in database and get user information
                db.collection("users").whereEqualTo("UID", mAuth.currentUser!!.uid).get()
                    .addOnSuccessListener{ Udocuments ->
                        Log.d("found UID", Udocuments.toString())
                        for(Udocument in Udocuments) {
                            user = Udocument.toObject<User>()
                            user!!.email = mAuth.currentUser!!.email.toString()
                        }
                        if (user != null)
                        {
                            val name = user!!.name
                            val nameStr = name.toString()
                            val region= user!!.region
                            val regionStr = region.toString()

                            userName = findViewById(R.id.title_merchant_dashboard)
                            userName!!.title = nameStr
                            userName!!.subtitle = regionStr


                            var num = 0;
                            for (i in listOfCoupons!!.indices) {

                                if (listOfCoupons!![i].vendor.equals(user!!.UID)) {
                                    listOfCoupons!![i].coordinate = num++;
                                } else {
                                    listOfCoupons!![i].coordinate = -1 // Set coordinate to -1 for coupons with different vendor names
                                }
                            }

                            bundle = Bundle().apply {
                                putParcelableArrayList("coupons", ArrayList(listOfCoupons.filter { it.coordinate != -1 }))
                                putParcelable("user", user)
                            }

                            navigationView = findViewById(R.id.merchant_dashboard_tab_layout)
                            val tab = navigationView!!.getTabAt(1)
                            tab?.select()

                            navigationView!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                                override fun onTabSelected(tab: TabLayout.Tab?) {
                                    var fragment: Fragment? = null

                                    when (tab!!.position) {

                                        0 -> {fragment = MerchantProfileFragment();
                                            fragment.arguments = bundle
                                        }
                                        1 -> {
                                            fragment = MerchantDealsFragment();
                                            fragment.arguments = bundle
                                        }

                                        2 -> fragment = MerchantMetricsFragment();

                                    }


                                    if (fragment != null) {
                                        Log.d("TAG", fragment.toString())
                                        val fragmentTransaction = supportFragmentManager.beginTransaction()
                                        fragmentTransaction.replace(
                                            R.id.merchant_dashboard_content,
                                            fragment,
                                            ""
                                        )
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

                            // When we open the application first
                            // time the fragment should be shown to the user
                            // in this case it is home fragment
                            val fragment = MerchantDealsFragment()
                            fragment.arguments = bundle

                            val fragmentTransaction = supportFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.merchant_dashboard_content, fragment, "")
                            fragmentTransaction.commit()


                        }
                    }
                    .addOnFailureListener {
                        Log.d("didnt find UID", user.toString())
                        // if they dont have anything, just fill with null for now
                        user = User("null","null",listOf("null"),listOf("null"), "null","null","null","null","null", "null", "null", "null")
                    }









            }
    }


}

