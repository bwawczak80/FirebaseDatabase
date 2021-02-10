package com.ebookfrenzy.firebasedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val adapterList: List<ListItem>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount() = adapterList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = adapterList[position]

        holder.textView1.text = currentItem.nameTxt
        holder.textView2.text = currentItem.speciesTxt
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.txtView1)
        val textView2: TextView = itemView.findViewById(R.id.txtView2)
    }

}