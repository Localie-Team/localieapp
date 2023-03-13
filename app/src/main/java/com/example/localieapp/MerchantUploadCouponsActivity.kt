package com.example.localieapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class MerchantUploadCouponsActivity : AppCompatActivity() {

    private var name: TextInputEditText? = null
    private var value: TextInputEditText? = null
    private var date: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_merchant_upload_coupons)

        name = findViewById(R.id.merchant_upload_coupons_name)
        value = findViewById(R.id.merchant_upload_coupons_value)
        date = findViewById(R.id.merchant_upload_coupons_date)

        date?.setOnClickListener(View.OnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
            }
            }
        )

    }


}