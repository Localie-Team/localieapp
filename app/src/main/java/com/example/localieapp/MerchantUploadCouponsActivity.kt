package com.example.localieapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MerchantUploadCouponsActivity : AppCompatActivity() {

    private var name: TextInputEditText? = null
    private var value: TextInputEditText? = null
    private var date: TextView? = null
    private var upload: Button? = null

    private var db = Firebase.firestore;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_merchant_upload_coupons)

        name = findViewById(R.id.merchant_upload_coupons_name)
        value = findViewById(R.id.merchant_upload_coupons_value)
        date = findViewById(R.id.merchant_upload_coupons_date)
        upload = findViewById(R.id.merchant_upload_coupons_upload_button)

        var dateRangePicker: MaterialDatePicker<Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select dates")
            .setSelection(
                Pair(MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds() + (1000 * 60 * 60 * 24 * 7)
            ))
            .build()

        date?.setOnClickListener(View.OnClickListener {
            var defaultSelection = Pair(MaterialDatePicker.todayInUtcMilliseconds(),
                MaterialDatePicker.todayInUtcMilliseconds() + (1000 * 60 * 60 * 24 * 7)
            )

            if (dateRangePicker.selection != null)
            {
                defaultSelection = Pair(dateRangePicker.selection!!.first, dateRangePicker.selection!!.second)
            }

            dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(defaultSelection)
                .build()

            dateRangePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            dateRangePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val dates = dateFormatter.format(it.first) + " - " + dateFormatter.format(it.second) ;
                date!!.text = dates;
            }

            // Setting up the event for when cancelled is clicked
            dateRangePicker.addOnNegativeButtonClickListener {
            }

            // Setting up the event for when back button is pressed
            dateRangePicker.addOnCancelListener {
            }
            }
        )

        upload?.setOnClickListener(View.OnClickListener {
            val dateFormatter = SimpleDateFormat("MM/dd/yyyy")
            val coupon = hashMapOf(
                "date_issued" to dateFormatter.format(MaterialDatePicker.todayInUtcMilliseconds()),
                "key_words" to arrayListOf("Keyword"),
                "value" to value!!.getText().toString(),
                "product" to name!!.getText().toString(),
                "product_code" to 12345,
                "vendor" to "Current Merchant Name"
            )

            // Add a new document with a generated ID
            db.collection("coupons")
                .add(coupon)
                .addOnSuccessListener {
                    Toast.makeText(
                        this@MerchantUploadCouponsActivity,
                        "Uploaded Coupon",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { Toast.makeText(this@MerchantUploadCouponsActivity, "Error", Toast.LENGTH_LONG).show()
                }

        })

    }


}