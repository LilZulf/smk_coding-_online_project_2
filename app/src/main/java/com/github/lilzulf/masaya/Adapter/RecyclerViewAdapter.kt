package com.github.lilzulf.masaya.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lilzulf.masaya.Object.TargetObject
import com.github.lilzulf.masaya.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.target_item.*

class RecyclerViewAdapter(private val context: Context, private val items : ArrayList<TargetObject>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.target_item, parent, false)
    )
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position))
    }
    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: TargetObject) {
            txtTargetTittle.text = item.tittle
        }
    }
}
