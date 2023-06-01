package com.example.localieapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class MerchantUploadCouponsActivity : AppCompatActivity() {

    private var name: TextInputEditText? = null
    private var value: TextInputEditText? = null
    private var date: TextView? = null
    private var upload: Button? = null
    private var select: Button? = null
    private var imageURI: Uri? =null



    private var db = Firebase.firestore;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_merchant_upload_coupons)

        name = findViewById(R.id.merchant_upload_coupons_name)
        value = findViewById(R.id.merchant_upload_coupons_value)
        date = findViewById(R.id.merchant_upload_coupons_date)
        select = findViewById(R.id.merchant_upload_coupons_select_image_button)
        upload = findViewById(R.id.merchant_upload_coupons_upload_button)

        var dateRangePicker: MaterialDatePicker<Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select dates")
            .setSelection(
                Pair(MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds() + (1000 * 60 * 60 * 24 * 7)
            ))
            .build()

        select?.setOnClickListener {
            selectImage()
        }
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
                "vendor" to FirebaseAuth.getInstance().currentUser!!.uid
            )
            uploadImage(coupon)



        })

    }

    private fun uploadImage(coupon: HashMap<String,java.io.Serializable>){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("coupon_icons/$filename")

        storageReference.putFile(imageURI!!)
            .addOnCompleteListener(){
                val imageURL= storageReference.getDownloadUrl().addOnCompleteListener(){ task ->
                    coupon["url"] = task.getResult().toString()
                    findViewById<ImageView>(R.id.FirebaseImage).setImageURI(null)
                    Toast.makeText(
                        this@MerchantUploadCouponsActivity,
                        "Uploaded Image",
                        Toast.LENGTH_LONG
                    ).show()
                    if(progressDialog.isShowing) progressDialog.dismiss()

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
                }

            }.addOnFailureListener(){
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@MerchantUploadCouponsActivity, "Error", Toast.LENGTH_LONG)
            }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            imageURI = data?.data!!
            findViewById<ImageView>(R.id.FirebaseImage).setImageURI(imageURI)
        }
    }


}