package com.example.localieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.localieapp.databinding.FragmentFirst2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class First2Fragment : Fragment() {

private var _binding: FragmentFirst2Binding? = null
    private var strtext: String? = null
    private var mAuth: FirebaseAuth? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


      _binding = FragmentFirst2Binding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore;
        mAuth = FirebaseAuth.getInstance()

        binding.buttonFirst.setOnClickListener {
            db.collection("permissions").whereEqualTo("UID", mAuth!!.currentUser!!.uid).get()
                .addOnSuccessListener { permission ->
                    Log.d("splash:", permission.toString())
                    for(P in permission){
                        Log.d("Splash:", P.data!!.get("permissions").toString())
                        //Checks if permission of user is Merchant
                        if( P.data!!.get("permissions").toString() == "Merchant"){
                            Log.d("Merchant:", "right here!")
                            val mainIntent = Intent(activity, MerchantDashboardActivity::class.java)
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(mainIntent)

                            //if the user has a permission but it isnt Merchant, Provider, or Admin then by default they are sent to consumer
                        }else{
                            Log.d("Consumer:", "right here!")
                            val mainIntent = Intent(activity, ConsumerDashboardActivity::class.java)
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(mainIntent)

                        } }
                    // If the user has no permissions then they are a consumer
                    if(permission.size() == 0){
                        Log.d("Consumer:", "right here!")
                        val mainIntent = Intent(activity, ConsumerDashboardActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(mainIntent)

                    }
                }
                .addOnFailureListener{
                    //If the check fails for any reason, then they are a consumer ( change this to an error)
                    Log.d("Consumer:", "right here!")
                    val mainIntent = Intent(activity, ConsumerDashboardActivity::class.java)
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(mainIntent)


                }
        }
        val bundle = arguments
        Log.d("Bundle11", bundle.toString())

//        val data = arguments?.getString("edttext")
//        val textView = binding.
//        Log.d("textView:", textView.toString())
//        textView.text = data
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}