package com.github.lilzulf.masaya.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lilzulf.masaya.Object.DataGrate
import com.github.lilzulf.masaya.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.grate_item2.*


class GrateAdapeter2(private val context: Context, private val items:
List<DataGrate>, private val listener: (DataGrate)-> Unit) :
    RecyclerView.Adapter<GrateAdapeter2.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(context, LayoutInflater.from(context).inflate(
            R.layout.grate_item2,
            parent, false))
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener)
    }
    class ViewHolder(val context: Context, override val containerView : View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: DataGrate, listener: (DataGrate) -> Unit) {
           txtGrateTittle.setText(item.text)
            txtGrateDate.setText(item.date)
        }
    }
}