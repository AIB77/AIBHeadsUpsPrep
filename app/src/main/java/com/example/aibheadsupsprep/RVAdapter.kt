package com.example.aibheadsupsprep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aibheadsupsprep.model.Celebrity
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter(private var celebrities: ArrayList<Celebrity>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val celebrity = celebrities[position]

        holder.itemView.apply {
            tvName.text = celebrity.name
            tvTaboo1.text = celebrity.taboo1
            tvTaboo2.text = celebrity.taboo2
            tvTaboo3.text = celebrity.taboo3
        }
    }

    override fun getItemCount() = celebrities.size

    fun update(celebrities: ArrayList<Celebrity>){
        this.celebrities = celebrities
        notifyDataSetChanged()
    }
}