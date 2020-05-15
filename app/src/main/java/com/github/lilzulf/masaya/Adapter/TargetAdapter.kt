package com.github.lilzulf.masaya.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.lilzulf.masaya.Object.DataItem
import com.github.lilzulf.masaya.R
import com.github.lilzulf.masaya.Util.tampilToast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.target_item.*

class TargetAdapter(private val context: Context, private val items:
List<DataItem>, private val listener: (DataItem)-> Unit) :
    RecyclerView.Adapter<TargetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(context, LayoutInflater.from(context).inflate(
            R.layout.target_item,
            parent, false))
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener)
    }
    class ViewHolder(val context: Context, override val containerView : View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: DataItem, listener: (DataItem) -> Unit) {
            txtTargetTittle.text = item.tittle
            txtTargetTittle.setOnClickListener {
                tampilToast(context,txtTargetTittle.text.toString())
            }
        }
    }
}