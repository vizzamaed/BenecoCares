package com.example.benecocares

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addAreaBtn: Button = view.findViewById(R.id.addAreaBtn)

        addAreaBtn.setOnClickListener {
            val showAddAreas = AddAreasFragment()
            showAddAreas.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }

    }

}
