package com.example.benecocares

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    private var selectedFragmentId: Int = R.id.bottom_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        if (savedInstanceState != null) {
            selectedFragmentId = savedInstanceState.getInt("selectedFragmentId", R.id.bottom_home)
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    selectedFragmentId = R.id.bottom_home
                    true
                }
                R.id.bottom_report -> {
                    replaceFragment(ReportFragment())
                    selectedFragmentId = R.id.bottom_report
                    true
                }
                R.id.bottom_calculator -> {
                    replaceFragment(CalculatorFragment())
                    selectedFragmentId = R.id.bottom_calculator
                    true
                }
                else -> false
            }
        }

        if (supportFragmentManager.findFragmentById(R.id.frame_container) == null) {
            when (selectedFragmentId) {
                R.id.bottom_home -> replaceFragment(HomeFragment())
                R.id.bottom_report -> replaceFragment(ReportFragment())
                R.id.bottom_calculator -> replaceFragment(CalculatorFragment())
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedFragmentId", selectedFragmentId)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }
}
