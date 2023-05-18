package com.example.localieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.localieapp.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var user: User? = null
private var storage: FirebaseStorage? = null

private val db = Firebase.firestore;

/**
 * A simple [Fragment] subclass.
 * Use the [MerchantProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class MerchantProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var settings: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = arguments?.getParcelable("user")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings = view.findViewById(R.id.profile_settings_merchant)

        settings?.setOnClickListener(View.OnClickListener {
            val mainIntent = Intent(activity, MerchantSettingActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainIntent)
        })
        Log.d("this is User", user.toString())

        val location = user?.location
        val locationStr = location.toString()

        val locationBlock = view.findViewById<TextView>(R.id.merchant_profile_location)
        locationBlock.text = locationStr

        val type = user?.type
        val typeStr = type.toString()

        val typeBlock = view.findViewById<TextView>(R.id.merchant_profile_type)
        typeBlock.text = typeStr

        val desc = user?.description
        val descStr = desc.toString()

        val descBlock = view.findViewById<TextView>(R.id.merchant_profile_desc)
        descBlock.text = descStr

        val pic = user!!.profile_pic.toString()
        val picMer = view.findViewById<ImageView>(R.id.profile_image_merchant)

        val desiredWidth = 800 // Specify the desired width in pixels
        val desiredHeight = 800 // Specify the desired height in pixels

        Glide.with(requireContext())
            .load(pic)
            .override(desiredWidth, desiredHeight)
            .centerCrop() // Optional: Apply center crop if needed
            .into(picMer)

    }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment MerchantProfile.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                MerchantProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    }
