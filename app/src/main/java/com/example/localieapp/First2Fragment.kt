package com.example.localieapp

import android.content.Intent
import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.localieapp.databinding.FragmentFirstBinding
import com.example.localieapp.model.Coupon

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFirstBinding? = null
    private var coupon: Coupon? = null
    private var couponName: TextView? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
//            coupons = it.getParcelableArrayList<Coupon>("coupons")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("args",arguments.toString())
        coupon = arguments?.getParcelable("coupon")

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
//        val textView = binding.textview_first
//
//        // Set the text of the TextView
//        textView.text = coupon!!.productName
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var textView: TextView? = null
        textView = view.findViewById(R.id.textview_first)
//
//        // Set the text of the TextView
//        textView.text = coupon!!.productName
        textView.text = "Coupon Details Go Here!"

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_First2Fragment_to_Second2Fragment)
            val mainIntent = Intent(activity, ConsumerDashboardActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mainIntent.putExtra("Current_Fragment", "Consumer_Deals")
            startActivity(mainIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
