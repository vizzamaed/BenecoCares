package com.example.benecocares

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import android.view.WindowManager
import com.example.benecocares.R

class AddAreasFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_add_areas, container, false)

        // Setup AutoCompleteTextView with dropdown
        val municipalities = resources.getStringArray(R.array.municipalities)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, municipalities)

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewMunicipality)
        autoCompleteTextView.setAdapter(arrayAdapter)

        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }


}
