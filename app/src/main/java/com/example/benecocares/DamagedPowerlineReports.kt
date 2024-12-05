package com.example.benecocares

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.app.TimePickerDialog
import java.util.Calendar


class DamagedPowerlineReports : AppCompatActivity() {
    private lateinit var etlocation: EditText
    private lateinit var ettime: EditText
    private lateinit var etdescription: EditText
    private lateinit var submitDamagedBtn: Button

    private val database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damaged_powerline_reports)

        etlocation = findViewById(R.id.locationTextBox)
        ettime = findViewById(R.id.timeTextBox)
        etdescription = findViewById(R.id.descriptionTextBox)
        submitDamagedBtn = findViewById(R.id.submitDamagedBtn)

        ettime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                this,
                R.style.CustomTimePickerDialog,
                { _, hourOfDay, minute ->
                    val amPm = if (hourOfDay < 12) "AM" else "PM"
                    val hour = if (hourOfDay == 0 || hourOfDay == 12) 12 else hourOfDay % 12
                    val formattedTime = String.format("%02d:%02d %s", hour, minute, amPm)
                    ettime.setText(formattedTime)
                },
                currentHour,
                currentMinute,
                false
            )

            timePicker.show()
        }
        submitDamagedBtn.setOnClickListener {
            val slocation = etlocation.text.toString().trim()
            val stime = ettime.text.toString().trim()
            val sdescription = etdescription.text.toString().trim()

            if (slocation.isEmpty() || stime.isEmpty() || sdescription.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val damagedPowerlineReportsMap = hashMapOf(
                "location" to slocation,
                "time" to stime,
                "description" to sdescription,
                "submittedAt" to FieldValue.serverTimestamp()
            )


            val documentId = System.currentTimeMillis().toString()

            database.collection("Damaged Powerline Reports").document(documentId).set(damagedPowerlineReportsMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Your report has been successfully submitted", Toast.LENGTH_SHORT).show()
                    etlocation.text.clear()
                    ettime.text.clear()
                    etdescription.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit your report", Toast.LENGTH_SHORT).show()
                }
        }
    }

}
