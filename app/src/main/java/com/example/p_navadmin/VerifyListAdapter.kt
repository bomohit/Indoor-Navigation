package com.example.p_navadmin

import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_verify_list.view.*

class VerifyListAdapter(private val eventList: MutableList<EventList>) : RecyclerView.Adapter<VerifyListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val event: TextView = itemView.listEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_verify_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = eventList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.event.text = eventList[position].store
        d("firebase", "${eventList[position].store}")
    }


}
