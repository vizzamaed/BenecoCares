package com.example.benecocares

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class ReportFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val powerOutageBtn: ImageButton = view.findViewById(R.id.powerOutageBtn)
        val equipmentFailureBtn: ImageButton = view.findViewById(R.id.equipmentFailureBtn)
        val damagedPowerlinePoleBtn: ImageButton = view.findViewById(R.id.damagedPowerlinePoleBtn)

        powerOutageBtn.setOnClickListener {
            val intent = Intent(activity, PowerOutageReports::class.java)
            startActivity(intent)
        }

        damagedPowerlinePoleBtn.setOnClickListener {
            val intent = Intent(activity, DamagedPowerlineReports::class.java)
            startActivity(intent)
        }

        equipmentFailureBtn.setOnClickListener {
            val intent = Intent(activity, EquipmentFailureReports::class.java)
            startActivity(intent)
        }

    }
}
