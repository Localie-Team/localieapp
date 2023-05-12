package com.example.localieapp

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
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
        val textView2 = findViewById<TextView>(R.id.description)
        textView2.text = passedItem!!.vendor + "\n\n" + passedItem!!.coupon_value + "\n\n" + passedItem!!.date_issued
//        val  imageView = findViewById<ImageView>(R.id.imageView2)
        var passedItem2 : ByteArray = intent.getByteArrayExtra("Coupon2")!!
        val bmp = BitmapFactory.decodeByteArray(passedItem2, 0, passedItem2.size)
        val image = findViewById<View>(R.id.imageView2) as ImageView
//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.width, image.height, false))
//        image.setImageDrawable(BitmapDrawable(mContext.getResources(), bmp))
        image.setImageBitmap(bmp)




    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_coupon_details2)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}