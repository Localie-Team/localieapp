package com.example.localieapp.adapter

import InnerRecyclerViewAdapter
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.R


//For expandable list view use BaseExpandableListAdapter
class ExpandableGridListAdapter(
    context: Context, listDataHeader: List<String>,
    listChildData: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {
    private val _context: Context
    private val header // header titles
            : List<String>
    var recyclerView: RecyclerView? = null

    // Child data in format of header title, child title
    private val child: HashMap<String, List<String>>

    init {
        _context = context
        header = listDataHeader
        child = listChildData
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): Any {

        // This will return the child
        return child[header[groupPosition]]!![childPosititon]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup
    ): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(
                R.layout.childs, parent,
                false
            )
        }
//        v = LayoutInflater.from(parent.context).inflate(R.layout.childs, parent, false)
        val groupname = header[groupPosition]
        recyclerView = convertView!!.findViewById(R.id.recyclerview)
        val sbc = GridAdapter(
            _context,
            child[groupname]!!
        )
        recyclerView!!.layoutManager = GridLayoutManager(_context, 3)
        recyclerView!!.adapter = sbc
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {

        // return children count
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {

        // Get header position
        return header[groupPosition]
    }

    override fun getGroupCount(): Int {

        // Get header size
        return header.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup?
    ): View? {

        // Getting header title
        var convertView: View? = convertView
        val headerTitle = getGroup(groupPosition) as String

        // Inflating header layout and setting text
        if (convertView == null) {
            val infalInflater = _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.adapter_list_group, parent, false)
        }
        val lblListHeader = convertView!!.findViewById(R.id.header_title) as TextView
        lblListHeader.text = headerTitle
        lblListHeader.setTypeface(null, Typeface.BOLD)


        // If group is expanded then change the text into bold and change the
        // icon
//        if (isExpanded) {
//            header_text.setTypeface(null, Typeface.BOLD)
//            header_text.setCompoundDrawablesWithIntrinsicBounds(
//                0, 0,
//                R.drawable.ic_baseline_keyboard_arrow_up_24, 0
//            )
//        } else {
//            // If group is not expanded then change the text back into normal
//            // and change the icon
//            header_text.setTypeface(null, Typeface.NORMAL)
//            header_text.setCompoundDrawablesWithIntrinsicBounds(
//                0, 0,
//                R.drawable.ic_baseline_keyboard_arrow_down_24, 0
//            )
//        }
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}