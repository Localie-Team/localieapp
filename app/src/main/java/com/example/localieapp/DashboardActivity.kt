package com.example.localieapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class DashboardActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var storage: FirebaseStorage? = null
    var firebaseUser: FirebaseUser? = null
    var myuid: String? = null
    var navigationView: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Firebase Storage init
        storage = Firebase.storage

        firebaseAuth = FirebaseAuth.getInstance()

        navigationView = findViewById(R.id.dashboard_tab_layout)
        val tab = navigationView!!.getTabAt(1)
        tab?.select()

        navigationView!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                    var fragment: Fragment? = null

                    when (tab!!.position) {
                    
                        0 -> fragment = ProfileFragment();
                        1 -> fragment = DealsFragment();
                        2-> fragment = EarnFragment();

                    }


                    if(fragment != null) {
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

        // When we open the application first
        // time the fragment should be shown to the user
        // in this case it is home fragment
        val fragment = DealsFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, fragment, "")
        fragmentTransaction.commit()
    }

}