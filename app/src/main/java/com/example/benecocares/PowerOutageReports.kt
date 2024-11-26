package com.example.benecocares

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.benecocares.databinding.ActivityPowerOutageReportsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PowerOutageReports : AppCompatActivity() {
    private lateinit var binding: ActivityPowerOutageReportsBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPowerOutageReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitOutageBtn.setOnClickListener {
            Log.d("PowerOutageReports", "Submit button clicked")

            val location = binding.locationTextBox.text.toString()
            val time = binding.timeTextBox.text.toString()
            val description = binding.descriptionTextBox.text.toString()

            Log.d(
                "PowerOutageReports",
                "Location: $location, Time: $time, Description: $description"
            )

            if (location.isEmpty() || time.isEmpty() || description.isEmpty()) {
                Log.d("PowerOutageReports", "Input fields are empty!")
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed with saving data to Firebase
            database = FirebaseDatabase.getInstance().getReference("Power Outage Reports")
            val powerOutageData = PowerOutageData(location, time, description)

            Log.d("PowerOutageReports", "Saving data to Firebase...")

            database.child(location).setValue(powerOutageData)
                .addOnSuccessListener {
                    Log.d("PowerOutageReports", "Data saved successfully!")
                    binding.locationTextBox.text.clear()
                    binding.timeTextBox.text.clear()
                    binding.descriptionTextBox.text?.clear()
                    Toast.makeText(
                        this,
                        "Your report is successfully submitted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Log.e("PowerOutageReports", "Failed to submit data", e)
                    Toast.makeText(this, "Failed to submit", Toast.LENGTH_SHORT).show()
                }
        }

    }
}
