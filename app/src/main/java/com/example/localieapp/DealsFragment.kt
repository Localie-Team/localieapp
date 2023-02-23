package com.example.localieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DealsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var pDrop: ImageView? = null
    private var sDrop: ImageView? = null
    private var aDrop: ImageView? = null
    var pState = false;
    var sState = false;
    var aState = false;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pDrop = view.findViewById(R.id.deals_products_arrow);
        sDrop = view.findViewById(R.id.deals_services_arrow);
        aDrop = view.findViewById(R.id.deals_attractions_arrow);

        pDrop?.setOnClickListener {

            if (!pState) {
                pDrop?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                pState = true;
            }
            else {
                pDrop?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                pState = false;
            }
        }

        sDrop?.setOnClickListener {
            if (!sState) {
                sDrop?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                sState = true;
            }
            else {
                sDrop?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                sState = false;
            }
        }

        aDrop?.setOnClickListener {
            if (!aState) {
                aDrop?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                aState = true;
            }
            else {
                aDrop?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                aState = false;
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DealsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DealsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}