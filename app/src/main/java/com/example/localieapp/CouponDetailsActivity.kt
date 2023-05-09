package com.example.localieapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.localieapp.databinding.ActivityCouponDetails2Binding
import com.example.localieapp.databinding.ContentCouponDetails2Binding
import com.example.localieapp.model.Coupon
import com.google.android.material.snackbar.Snackbar


class CouponDetailsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCouponDetails2Binding
    private lateinit var binding2: ContentCouponDetails2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var passedItem: Coupon? = null
        passedItem = @Suppress("DEPRECATION") intent.getParcelableExtra("Coupon")
        val bundle = Bundle()
        bundle.putString("edttext", "From Activity")
        Log.d("BundleAct", bundle.toString())

// set Fragmentclass Arguments
// set Fragmentclass Arguments

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_coupon_details2)?.getChildFragmentManager()?.fragments?.get(0)
        fragment?.arguments = bundle

        binding = ActivityCouponDetails2Binding.inflate(layoutInflater)
         setContentView(binding.root)

//        binding2 = ContentCouponDetails2Binding.inflate(layoutInflater)
//        setContentView(binding2.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_coupon_details2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
//        val textView = binding.textviewDetails
//        textView.text = passedItem!!.productName
        val textView = findViewById<TextView>(R.id.textView2)
        textView.text = passedItem!!.productName




    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_coupon_details2)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}