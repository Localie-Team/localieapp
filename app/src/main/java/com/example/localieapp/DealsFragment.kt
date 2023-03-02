package com.example.localieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ExpandableListView.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.ExpandableListAdapter
import com.example.localieapp.adapter.ExpandableGridListAdapter
import com.example.localieapp.adapter.GridAdapter
import com.example.localieapp.data.Datasource
import com.example.localieapp.model.Coupon


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

    private var recyclerView: RecyclerView? = null
    private var coupons: List<Coupon>? = null

    private var expListView: ExpandableListView? = null
    private var adapter: ExpandableGridListAdapter? = null

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

        coupons = Datasource().loadCoupons()

        recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
        recyclerView!!.adapter = GridAdapter(requireContext(), coupons!!);
        recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 3);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView!!.setHasFixedSize(true)

        expListView = view.findViewById(R.id.deals_exp_list_view)

        // Setting group indicator null for custom indicator
        expListView!!.setGroupIndicator(null);

        setItems();
        setListener();

//        expListView = view.findViewById(R.id.deals_exp_list_view)
//
//        var (listDataParent, listDataChild) = Datasource().createListData()
//
//        var listAdapter = ExpandableListAdapter(requireContext(), listDataParent, listDataChild)
//        expListView!!.setAdapter(listAdapter)
//
//        // Expandable Listview on group click listener
//        expListView!!.setOnGroupClickListener(OnGroupClickListener { parent, v, groupPosition, id -> // TODO GroupClickListener work
//            false
//        })
//
//        // Expandable Listview Group Expanded Listener
//        expListView!!.setOnGroupExpandListener(OnGroupExpandListener {
//            // TODO GroupExpandListener work
//        })
//
//        // Expandable Listview Group Collapsed listener
//        expListView!!.setOnGroupCollapseListener(OnGroupCollapseListener {
//            // TODO GroupCollapseListener work
//        })
//
//        // Expandable Listview on child click listener
//        expListView!!.setOnChildClickListener(OnChildClickListener { parent, v, groupPosition, childPosition, id ->
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

    fun setItems() {

        // Array list for header
        val header = ArrayList<String>()

        // Array list for child items
        val child1: MutableList<String> = ArrayList()
        val child2: MutableList<String> = ArrayList()
        val child3: MutableList<String> = ArrayList()

        // Hash map for both header and child
        val hashMap = HashMap<String, List<String>>()

        // Adding headers to list
        for (i in 1..3) {
            header.add("Group $i")
        }
        // Adding child data
        for (i in 1..4) {
            child1.add("Group 1  -  : Child$i")
        }
        // Adding child data
        for (i in 1..4) {
            child2.add("Group 2  -  : Child$i")
        }
        // Adding child data
        for (i in 1..5) {
            child3.add("Group 3  -  : Child$i")
        }

        // Adding header and childs to hash map
        hashMap[header[0]] = child1
        hashMap[header[1]] = child2
        hashMap[header[2]] = child3

        adapter = ExpandableGridListAdapter(requireContext(), header, hashMap)

        // Setting adpater over expandablelistview
        expListView!!.setAdapter(adapter)
    }

    // Setting different listeners to expandablelistview
    fun setListener() {

        // This listener will show toast on group click
        expListView!!.setOnGroupClickListener(OnGroupClickListener { listview, view, group_pos, id ->
            Toast.makeText(
                requireContext(),
                "You clicked : " + adapter!!.getGroup(group_pos),
                Toast.LENGTH_SHORT
            ).show()
            false
        })

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expListView!!
            .setOnGroupExpandListener(object : OnGroupExpandListener {
                // Default position
                var previousGroup = -1
                override fun onGroupExpand(groupPosition: Int) {
                    if (groupPosition != previousGroup) // Collapse the expanded group
                        expListView!!.collapseGroup(previousGroup)
                    previousGroup = groupPosition
                }
            })

        // This listener will show toast on child click
        expListView!!.setOnChildClickListener(OnChildClickListener { listview, view, groupPos, childPos, id ->
            Toast.makeText(
                requireContext(),
                "You clicked : " + adapter!!.getChild(groupPos, childPos),
                Toast.LENGTH_SHORT
            ).show()
            false
        })
    }
}