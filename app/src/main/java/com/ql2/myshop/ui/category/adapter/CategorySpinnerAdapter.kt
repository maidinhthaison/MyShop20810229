package com.ql2.myshop.ui.category.adapter

import android.content.Context
import android.widget.ArrayAdapter

class CategorySpinnerAdapter(context: Context, resource: Int, objects: Array<out String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getCount(): Int {
        return super.getCount()
    }
}