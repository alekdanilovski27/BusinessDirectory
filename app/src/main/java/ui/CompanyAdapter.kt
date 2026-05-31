package com.example.businessdir.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.businessdir.R
import com.example.businessdir.data.Company

class CompanyAdapter(context: Context, private val companies: List<Company>)
    : ArrayAdapter<Company>(context, 0, companies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_company, parent, false)

        val company = companies[position]
        view.findViewById<TextView>(R.id.companyName).text = company.name
        view.findViewById<TextView>(R.id.companyAddress).text = company.address
        view.findViewById<TextView>(R.id.companyPhone).text = company.telephone
        view.findViewById<TextView>(R.id.companyWebsite).text = company.website

        return view
    }
}