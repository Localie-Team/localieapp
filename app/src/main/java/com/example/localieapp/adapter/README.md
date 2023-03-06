These are some of the resources that I used to create a lot of these template and adapter methods.
It also took a good deal of hacking, so things might not line up with the tutorial. I'm keeping this
in here just in case we need it to reference.

Also in case you wonder why there's a whole load of code we're not using.


https://stackoverflow.com/questions/51817460/expandable-listview-with-custom-gridlayout-in-it

grid layout expandable view

https://www.elsebazaar.com/blog/how-to-create-an-expandable-listview-in-fragments-in-android-studio/

https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the

https://stackoverflow.com/questions/26566954/square-layout-on-gridlayoutmanager-for-recyclerview

https://developer.android.com/codelabs/basic-android-kotlin-training-display-list-cards#2 (Wednesday, March 2nd, 2023)


Below is an explanation of some of the template code that's going to be left in here.

The ExpandableListAdapter is an Adapter for the ExpandableList class in the Android SDK. It's not a 
part of Material Design, so it's just raw Android. It should take the adapter_list_group and inflate
it when it's unexpanded and then inflate the adapter_list_item when it is expanded.

Code Below for ELA:

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


The GridAdapter is an adapter for a grid design. It's been changed to act as the InnerListAdapter
that works in conjuction with the ExpandableListGridAdapter. It used to be it's own thing and can
probably be changed back.

The ExpandableListGridAdapter is a combination of the ExpandableListAdapter and the GridAdapter.
It's basically the ExpandableListAdapter but instead of passing a value back as the children, it
passes a GridAdapter to adapter_grid_item which inflates when the adapter_list_item is expanded.

Some code below for ELGA:

//        expListView = view.findViewById(R.id.deals_exp_list_view)
//
//        // Setting group indicator null for custom indicator
//        expListView!!.setGroupIndicator(null);
//
//        setItems();
//        setListener();
//        

//    fun setItems() {
//
//        // Array list for header
//        val header = ArrayList<String>()
//
//        // Array list for child items
//        val child1: MutableList<String> = ArrayList()
//        val child2: MutableList<String> = ArrayList()
//        val child3: MutableList<String> = ArrayList()
//        val child4: MutableList<String> = ArrayList()
//
//
//        // Hash map for both header and child
//        val hashMap = HashMap<String, List<String>>()
//
//        // Adding headers to list
//        header.add("Products")
//        header.add("Services")
//        header.add("Attractions")
//        // Adding child data
//        for (i in 1..6) {
//            child1.add("Product Coupon $i")
//        }
//        // Adding child data
//        for (i in 1..6) {
//            child2.add("Service Coupon $i")
//        }
//        // Adding child data
//        for (i in 1..6) {
//            child3.add("Attraction Coupon $i")
//        }
//        for (i in 1..7) {
//            child4.add("Attraction Coupon $i")
//        }
//
//        // Adding header and childs to hash map
//        hashMap[header[0]] = child1
//        hashMap[header[1]] = child2
//        hashMap[header[2]] = child3
//        hashMap[header[2]] = child4
//
//        adapter = ExpandableListGridAdapter(requireContext(), header, hashMap)
//
//        // Setting adpater over expandablelistview
//        expListView!!.setAdapter(adapter)
//    }
//
//    // Setting different listeners to expandablelistview
//    fun setListener() {
//
//        // This listener will show toast on group click
//        expListView!!.setOnGroupClickListener(OnGroupClickListener { listview, view, group_pos, id ->
//            Toast.makeText(
//                requireContext(),
//                "You clicked : " + adapter!!.getGroup(group_pos),
//                Toast.LENGTH_SHORT
//            ).show()
//            false
//        })
//
//        // This listener will expand one group at one time
//        // You can remove this listener for expanding all groups
//        expListView!!
//            .setOnGroupExpandListener(object : OnGroupExpandListener {
//                // Default position
//                var previousGroup = -1
//                override fun onGroupExpand(groupPosition: Int) {
//                    if (groupPosition != previousGroup) // Collapse the expanded group
//                        expListView!!.collapseGroup(previousGroup)
//                    previousGroup = groupPosition
//                }
//            })
//
//        // This listener will show toast on child click
//        expListView!!.setOnChildClickListener(OnChildClickListener { listview, view, groupPos, childPos, id ->
//            Toast.makeText(
//                requireContext(),
//                "You clicked : " + adapter!!.getChild(groupPos, childPos),
//                Toast.LENGTH_SHORT
//            ).show()
//            false
//        })
//    }