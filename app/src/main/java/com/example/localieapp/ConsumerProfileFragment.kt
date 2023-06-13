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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.ProfileGridAdapter
import com.example.localieapp.model.Coupon
import com.example.localieapp.model.User
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsumerProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var settings: ImageView? = null

//    private var expListView: ExpandableListView? = null

    private var recyclerView: RecyclerView? = null
    private var coupons: List<Coupon>? = null

    private var user: User? = null
    private var firebaseAuth: FirebaseAuth? = null
    var userEmail: TextView? = null


    val db = Firebase.firestore;

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
        coupons = arguments?.getParcelableArrayList<Coupon>("coupons")
        user = arguments?.getParcelable("user")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()


        if (firebaseAuth!!.currentUser != null)
        {
            val emailStr = firebaseAuth!!.currentUser!!.email.toString()
            userEmail = view.findViewById(R.id.profile_text)
            userEmail!!.text = emailStr
        }

        settings = view.findViewById(R.id.profile_settings)
//        expListView = view.findViewById(R.id.profile_exp_list_view)

        settings?.setOnClickListener(View.OnClickListener {
            val mainIntent = Intent(activity, ConsumerSettingActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainIntent)
        })
        db.collection("users").whereEqualTo("UID", firebaseAuth!!.currentUser!!.uid).get()
            .addOnSuccessListener { Udocuments ->
                Log.d("found UID", Udocuments.toString())
                for (Udocument in Udocuments) {
                    user = Udocument.toObject<User>()
                    user!!.email = firebaseAuth!!.currentUser!!.email.toString()
                }
                var wins = ArrayList<Coupon>()
                var j = 0
                for (i in coupons!!.indices) {
                    if(user!!.win!!.contains(coupons!!.get(i).UID.toString()) ){
                        coupons!!.get(i).coordinate = j++
                        wins.add(coupons!!.get(i))
                    }
                }


                recyclerView = view.findViewById<RecyclerView>(R.id.profile_recycler_view);
//                recyclerView!!.adapter = ProfileGridAdapter(requireContext(), listOfCoupons!!);
                recyclerView!!.adapter = ProfileGridAdapter(requireContext(), wins);
                recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 3);

                // Use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView!!.setHasFixedSize(true)
//            }
            }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsumerProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}