package com.example.localieapp.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.localieapp.R

class ExpandableListAdapter(
    private val _context: Context,
    // List of headers
    private val _listDataHeader: List<String>,
    // Child data in format of header title, child title
    private val _listDataChild: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    // Function to get child data for a specific group and position
    override fun getChild(groupPosition: Int, childPosititon: Int): Any {
        return _listDataChild[_listDataHeader[groupPosition]]!!
            .get(childPosititon)
    }
    // Function to get the id of a child in a specific group
    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }
    // Function to create and return the view for a child
    override fun getChildView(
        groupPosition: Int, childPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup
    ): View {
        var convertView = convertView
        val childText = getChild(groupPosition, childPosition) as String
        if (convertView == null) {
            // if the view is not yet created, inflate the layout
            val infalInflater = _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.adapter_list_item, null)
        }
        // set the text of the child view
        val txtListChild = convertView!!.findViewById<View>(R.id.text_child) as TextView
        txtListChild.text = childText
        return convertView
    }

    // Function to get the number of children
    override fun getChildrenCount(groupPosition: Int): Int {
        return _listDataChild[_listDataHeader[groupPosition]]!!
            .size
    }

    // Function to get the header data
    override fun getGroup(groupPosition: Int): Any {
        return _listDataHeader[groupPosition]
    }
    // Function to get the number of headers
    override fun getGroupCount(): Int {
        return _listDataHeader.size
    }
    // Function to get the id of headers
    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }
    // Function to create and return the view for a header
    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup
    ): View {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as String
        if (convertView == null) {
            val infalInflater =
                _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.adapter_list_group, null)
        }
        // set the text of the header view and make it BOLD
        val lblListHeader = convertView!!
            .findViewById<View>(R.id.header_title) as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text = headerTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}