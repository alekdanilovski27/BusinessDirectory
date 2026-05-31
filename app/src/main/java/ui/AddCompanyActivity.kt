package com.example.businessdir.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.businessdir.R
import com.example.businessdir.data.Company
import com.example.businessdir.data.CompanyRepository
import kotlinx.coroutines.launch

class AddCompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)
        supportActionBar?.title = "Add Company"

        val etName = findViewById<EditText>(R.id.etName)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val etLat = findViewById<EditText>(R.id.etLatitude)
        val etLng = findViewById<EditText>(R.id.etLongitude)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etTel = findViewById<EditText>(R.id.etTelephone)
        val etWeb = findViewById<EditText>(R.id.etWebsite)
        val cbIndustry = findViewById<CheckBox>(R.id.cbIndustry)
        val cbFun = findViewById<CheckBox>(R.id.cbFun)
        val cbEducation = findViewById<CheckBox>(R.id.cbEducation)
        val cbServices = findViewById<CheckBox>(R.id.cbServices)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val company = Company(
                name = name,
                address = etAddress.text.toString(),
                latitude = etLat.text.toString().toDoubleOrNull() ?: 0.0,
                longitude = etLng.text.toString().toDoubleOrNull() ?: 0.0,
                email = etEmail.text.toString(),
                telephone = etTel.text.toString(),
                website = etWeb.text.toString(),
                isIndustry = cbIndustry.isChecked,
                isFun = cbFun.isChecked,
                isEducation = cbEducation.isChecked,
                isServices = cbServices.isChecked
            )
            lifecycleScope.launch {
                try {
                    CompanyRepository.insertCompany(company)
                    runOnUiThread {
                        Toast.makeText(this@AddCompanyActivity, "Company saved!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@AddCompanyActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}