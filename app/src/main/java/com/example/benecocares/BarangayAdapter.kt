package com.example.benecocares

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarangayAdapter(private val barangayList: ArrayList<BarangayDetails>, private val onDeleteClicked: (String) -> Unit) :
    RecyclerView.Adapter<BarangayAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = barangayList[position]
        holder.Name.text = currentItem.Name
        holder.Date.text = currentItem.Date
        holder.Time.text = currentItem.Time

        holder.deleteButton.setOnClickListener {
            onDeleteClicked(currentItem.Name)
        }
    }

    override fun getItemCount(): Int {
        return barangayList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.tvBarangayName)
        val Date: TextView = itemView.findViewById(R.id.tvDate)
        val Time: TextView = itemView.findViewById(R.id.tvTime)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete)
    }
}
