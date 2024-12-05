package com.example.benecocares

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var adapter: BarangayAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var barangayArrayList: ArrayList<BarangayDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        barangayArrayList = arrayListOf()

        adapter = BarangayAdapter(barangayArrayList) { barangayName ->
            deleteBarangay(barangayName)
        }

        recyclerView.adapter = adapter

        fetchBarangayData()

        val addAreaBtn: Button = view.findViewById(R.id.addAreaBtn)
        addAreaBtn.setOnClickListener {
            val intent = Intent(activity, AddAreas::class.java)
            startActivity(intent)
        }
    }

    private fun fetchBarangayData() {
        val database = FirebaseFirestore.getInstance()

        database.collection("USER")
            .addSnapshotListener { documents, exception ->
                if (exception != null) {
                    Toast.makeText(context, "Failed to load data: ${exception.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                barangayArrayList.clear()

                for (document in documents!!) {
                    val barangayName = document.id
                    val data = document.toObject(BarangayDetails::class.java)


                    data?.let {
                        it.Name = barangayName
                        barangayArrayList.add(it)
                    }
                }

                adapter.notifyDataSetChanged()
            }
    }

    private fun deleteBarangay(barangayName: String) {
        val database = FirebaseFirestore.getInstance()

        database.collection("USER").document(barangayName)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Barangay Deleted", Toast.LENGTH_SHORT).show()

                fetchBarangayData()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to delete: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

