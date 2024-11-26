package com.example.benecocares

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.example.benecocares.R.id.autoCompleteTextViewAppliances

class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        val appliances = resources.getStringArray(R.array.appliances)
        val wattages = resources.getStringArray(R.array.appliances_wattages)

        // Create a mapping from appliances to wattages
        val applianceWattages = appliances.zip(wattages.map { it.toInt() }).toMap()

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, appliances)
        val autoCompleteTextViewCalculator = view.findViewById<AutoCompleteTextView>(autoCompleteTextViewAppliances)
        autoCompleteTextViewCalculator.setAdapter(arrayAdapter)

        val wattageTextBox = view.findViewById<TextInputEditText>(R.id.WattageTextBox)

        // Set listener to update wattage when appliance is selected
        autoCompleteTextViewCalculator.setOnItemClickListener { _, _, position, _ ->
            val selectedAppliance = appliances[position]
            val wattage = applianceWattages[selectedAppliance]
            wattageTextBox.setText(wattage.toString())
        }


        val monthlyBillTextBox = view.findViewById<TextInputEditText>(R.id.monthlyBillTextBox)
        val hoursTextBox = view.findViewById<TextInputEditText>(R.id.HoursTextBox)
        val daysTextBox = view.findViewById<TextInputEditText>(R.id.DaysTextBox)
        val weeksTextBox = view.findViewById<TextInputEditText>(R.id.WeeksTextBox)

        val hourlyTextBox = view.findViewById<TextInputEditText>(R.id.Hourly)
        val dailyTextBox = view.findViewById<TextInputEditText>(R.id.Daily)
        val weeklyTextBox = view.findViewById<TextInputEditText>(R.id.Weekly)
        val monthlyTextBox = view.findViewById<TextInputEditText>(R.id.Monthly)

        val calculateBtn = view.findViewById<Button>(R.id.calculateBtn)
        val resetBtn = view.findViewById<Button>(R.id.resetBtn)

        calculateBtn.setOnClickListener {
            //user inputs
            val wattage = wattageTextBox.text.toString().toDoubleOrNull() ?: 0.0
            val hours = hoursTextBox.text.toString().toDoubleOrNull() ?: 0.0
            val days = daysTextBox.text.toString().toDoubleOrNull() ?: 0.0
            val weeks = weeksTextBox.text.toString().toDoubleOrNull() ?: 0.0

            // Calculate power consumption (kWh)
            val hourly = wattage / 1000 * hours
            val daily = hourly * days
            val weekly = daily * weeks
            val monthly = daily * 30 // Assume 30 days in a month

            // Display results
            hourlyTextBox.setText(String.format("%.2f", hourly))
            dailyTextBox.setText(String.format("%.2f", daily))
            weeklyTextBox.setText(String.format("%.2f", weekly))
            monthlyTextBox.setText(String.format("%.2f", monthly))
        }

        resetBtn.setOnClickListener {
            // Clear all
            wattageTextBox.text?.clear()
            hoursTextBox.text?.clear()
            daysTextBox.text?.clear()
            weeksTextBox.text?.clear()
            hourlyTextBox.setText("")
            dailyTextBox.setText("")
            weeklyTextBox.setText("")
            monthlyTextBox.setText("")

            autoCompleteTextViewCalculator.setText("")

            monthlyBillTextBox?.setText("")
        }

        return view
    }
}