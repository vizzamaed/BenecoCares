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
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import java.io.IOException
import java.util.Calendar


class DamagedPowerlineReports : AppCompatActivity() {
    private lateinit var etlocation: EditText
    private lateinit var ettime: EditText
    private lateinit var etdescription: EditText
    private lateinit var submitDamagedBtn: Button
    private lateinit var addImageBtn: Button
    private lateinit var selectedImageView: ImageView
    private var selectedImageUri: Uri? = null

    private val database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damaged_powerline_reports)

        etlocation = findViewById(R.id.locationTextBox)
        ettime = findViewById(R.id.timeTextBox)
        etdescription = findViewById(R.id.descriptionTextBox)
        submitDamagedBtn = findViewById(R.id.submitDamagedBtn)
        addImageBtn = findViewById(R.id.addImageBtn)
        selectedImageView = findViewById(R.id.selectedImageView)

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

        addImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
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

            if (selectedImageUri == null) {
                Toast.makeText(this, "Report submitted without photo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Report submitted with photo", Toast.LENGTH_SHORT).show()
            }

            etlocation.text.clear()
            ettime.text.clear()
            etdescription.text.clear()
            selectedImageView.visibility = View.GONE
            selectedImageUri = null


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                selectedImageView.setImageBitmap(bitmap)
                selectedImageView.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 100
    }

}
