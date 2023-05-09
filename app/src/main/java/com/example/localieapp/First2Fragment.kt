package com.example.localieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.localieapp.databinding.FragmentFirst2Binding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class First2Fragment : Fragment() {

private var _binding: FragmentFirst2Binding? = null
    private var strtext: String? = null
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

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_First2Fragment_to_Second2Fragment)
            val mainIntent = Intent(activity, ConsumerDashboardActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mainIntent.putExtra("Current_Fragment", "Consumer_Deals")
            startActivity(mainIntent)
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