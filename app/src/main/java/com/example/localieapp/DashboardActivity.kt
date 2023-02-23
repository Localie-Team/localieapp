package com.example.localieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.localieapp.ProfileFragment
import com.example.localieapp.DealsFragment
import com.example.localieapp.EarnFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DashboardActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    var firebaseUser: FirebaseUser? = null
    var myuid: String? = null
    var navigationView: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        firebaseAuth = FirebaseAuth.getInstance()
        navigationView = findViewById(R.id.dashboard_tab_layout)
        navigationView!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab!!.id) {

                        R.id.nav_profile ->
                        {
//                            ProfileFragment fragment = new ProfileFragment();
                            val fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content, ProfileFragment(), "");
                            fragmentTransaction.commit();
                        }

                        R.id.nav_deals ->
                        {
//                            DealsFragment fragment = new DealsFragment();
                            val fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content, DealsFragment(), "");
                            fragmentTransaction.commit();
                        }

                        R.id.nav_earn ->
                        {
//                            EarnFragment fragment = new EarnFragment();
                            val fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content, EarnFragment(), "");
                            fragmentTransaction.commit();
                        }
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