package com.example.localieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.ExpandableListAdapter
import com.example.localieapp.adapter.GridAdapter
import com.example.localieapp.data.Datasource
import com.example.localieapp.model.Coupon

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var settings: ImageView? = null

//    private var expListView: ExpandableListView? = null

    private var recyclerView: RecyclerView? = null
    private var coupons: List<Coupon>? = null

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings = view.findViewById(R.id.profile_settings)
//        expListView = view.findViewById(R.id.profile_exp_list_view)

        settings?.setOnClickListener(View.OnClickListener {
            val mainIntent = Intent(activity, EditUserSettings::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainIntent)
        })

        coupons = Datasource.loadCoupons();

        for (i in coupons!!.indices) {
//            print(i);
            coupons!![i].coordinate = i;
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.profile_recycler_view);
        recyclerView!!.adapter = GridAdapter(requireContext(), coupons!!);
        recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 3);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView!!.setHasFixedSize(true)

//        var (listDataParent, listDataChild) = Datasource().createListData()
//        var listAdapter = ExpandableListAdapter(requireContext(), listDataParent, listDataChild)
//
//        expListView!!.setAdapter(listAdapter)
//
//        // Expandable Listview on group click listener
//        expListView!!.setOnGroupClickListener(ExpandableListView.OnGroupClickListener { parent, v, groupPosition, id -> // TODO GroupClickListener work
//            false
//        })
//
//        // Expandable Listview Group Expanded Listener
//        expListView!!.setOnGroupExpandListener(ExpandableListView.OnGroupExpandListener {
//            // TODO GroupExpandListener work
//        })
//
//        // Expandable Listview Group Collapsed listener
//        expListView!!.setOnGroupCollapseListener(ExpandableListView.OnGroupCollapseListener {
//            // TODO GroupCollapseListener work
//        })
//
//        // Expandable Listview on child click listener
//        expListView!!.setOnChildClickListener(ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
//            false
//        })


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
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}