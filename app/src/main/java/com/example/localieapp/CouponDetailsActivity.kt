package com.example.localieapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.localieapp.databinding.ActivityCouponDetails2Binding
import com.example.localieapp.model.Coupon

class CouponDetailsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCouponDetails2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var passedItem: Coupon? = null
        passedItem = @Suppress("DEPRECATION") intent.getParcelableExtra("Coupon")

         binding = ActivityCouponDetails2Binding.inflate(layoutInflater)
         setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_coupon_details2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_coupon_details2)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}