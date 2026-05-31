package com.example.businessdir.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.businessdir.R
import com.example.businessdir.data.CompanyRepository
import kotlinx.coroutines.launch

class CompanyListFragment : Fragment() {

    companion object {
        fun newInstance(category: String): CompanyListFragment {
            return CompanyListFragment().apply {
                arguments = Bundle().apply { putString("category", category) }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_company_list, container, false)
        val listView = view.findViewById<ListView>(R.id.listView)
        val searchField = view.findViewById<EditText>(R.id.searchField)
        val category = arguments?.getString("category") ?: "services"

        fun loadData(search: String = "") {
            lifecycleScope.launch {
                try {
                    val companies = CompanyRepository.getCompaniesByCategory(category, search)
                    listView.adapter = CompanyAdapter(requireContext(), companies)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loadData()

        searchField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = loadData(s.toString())
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return view
    }
}