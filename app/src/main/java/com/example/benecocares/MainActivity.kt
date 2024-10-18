package com.example.benecocares

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_report -> {
                    replaceFragment(ReportFragment())
                    true
                }
                R.id.bottom_calculator -> {
                    replaceFragment(CalculatorFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment()) // Initial fragment
    }

    private fun replaceFragment(fragment: Fragment) { // Accept any Fragment
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}
