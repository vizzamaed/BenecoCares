package com.example.benecocares


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddAreas : AppCompatActivity() {
    private val database = FirebaseFirestore.getInstance()
    private var selectedBarangay: String? = null
    private var barangayDetails: Map<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_areas)

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewBaranggay)
        val setButton = findViewById<Button>(R.id.setAreaBtn)

        //Kunin yung data from firestore and display sa dropdown
        database.collection("Baranggay").get()
            .addOnSuccessListener { documents ->
                val baranggays = mutableListOf<String>()
                val barangayMap = mutableMapOf<String, Map<String, Any>>()

                for (document in documents) {
                    val barangayName = document.id
                    val barangayData = document.data

                    baranggays.add(barangayName)
                    barangayMap[barangayName] = barangayData
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, baranggays)
                autoCompleteTextView.setAdapter(adapter)

                autoCompleteTextView.setOnClickListener {
                    autoCompleteTextView.showDropDown()
                }

                autoCompleteTextView.setOnItemClickListener { parent, _, _, _ ->
                    val selectedText = autoCompleteTextView.text.toString()
                    if (barangayMap.containsKey(selectedText)) {
                        selectedBarangay = selectedText
                        barangayDetails = barangayMap[selectedText]
                    } else {
                        Toast.makeText(this, "Selected barangay not found.", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load data: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        setButton.setOnClickListener {
            if (!selectedBarangay.isNullOrEmpty()) {
                saveBarangayToFirestore(selectedBarangay!!)
            } else {
                Toast.makeText(this, "Please select a barangay first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Save selected barangay sa firestore
    private fun saveBarangayToFirestore(barangay: String) {
        val barangayData = hashMapOf("name" to barangay)

        if (barangayDetails != null) {
            barangayData["Time"] = barangayDetails?.get("Time") as? String ?: ""
            barangayData["Date"] = barangayDetails?.get("Date") as? String ?: ""
        }

        database.collection("USER")
            .document(barangay)
            .set(barangayData)
            .addOnSuccessListener {
                Toast.makeText(this, "Barangay '$barangay' saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save barangay: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
