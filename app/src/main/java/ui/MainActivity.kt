package com.example.businessdir.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.businessdir.R
import com.example.businessdir.data.CompanyRepository
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Business Directory"

        val tabs = listOf("Services", "Fun", "Industry", "Education")
        val categories = listOf("services", "fun", "industry", "education")

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment = CompanyListFragment.newInstance(categories[position])
            override fun getCount() = tabs.size
            override fun getPageTitle(position: Int): CharSequence = tabs[position]
        }

        tabLayout.setupWithViewPager(viewPager)

        setupGeolocation()
    }

    private fun setupGeolocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { userLocation ->
                    checkNearbyCompanies(userLocation)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        } else {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L).build()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, locationCallback, mainLooper)
        }
    }

    private fun checkNearbyCompanies(userLocation: Location) {
        lifecycleScope.launch {
            try {
                // Load all companies and check distance
                val allCategories = listOf("services", "fun", "industry", "education")
                val seen = mutableSetOf<Int>()
                for (cat in allCategories) {
                    val companies = CompanyRepository.getCompaniesByCategory(cat)
                    for (company in companies) {
                        if (seen.contains(company.id)) continue
                        seen.add(company.id)
                        val companyLocation = Location("").apply {
                            latitude = company.latitude
                            longitude = company.longitude
                        }
                        val distance = userLocation.distanceTo(companyLocation)
                        if (distance <= 50f) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity,
                                    "You are near ${company.name}!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            } catch (_: Exception) {}
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            startActivity(Intent(this, AddCompanyActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}