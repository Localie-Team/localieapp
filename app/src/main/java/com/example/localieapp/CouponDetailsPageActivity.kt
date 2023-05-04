package com.example.localieapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.localieapp.databinding.ActivityCouponDetailsPage2Binding
import com.example.localieapp.model.Coupon

class CouponDetailsPage : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCouponDetailsPage2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = Bundle();

        var passedItem: Coupon? = null
        passedItem = @Suppress("DEPRECATION") intent.getParcelableExtra("Coupon")
        bundle = Bundle().apply { putParcelable("coupon", passedItem) }
        var fragment: Fragment? = null
        fragment = First2Fragment();
        fragment.arguments = bundle


        binding = ActivityCouponDetailsPage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_coupon_details_page2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            passedItem!!.productName?.let {
                Snackbar.make(view, it, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_coupon_details_page2)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}