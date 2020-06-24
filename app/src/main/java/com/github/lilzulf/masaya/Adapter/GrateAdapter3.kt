package com.github.lilzulf.masaya.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lilzulf.masaya.Object.MyGrateModel
import com.github.lilzulf.masaya.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.grate_item1.*

class GrateAdapeter3(private val context: Context, private val items:
List<MyGrateModel>, private val listener: (MyGrateModel)-> Unit) :
    RecyclerView.Adapter<GrateAdapeter3.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(context, LayoutInflater.from(context).inflate(
            R.layout.grate_item1,
            parent, false))
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener)
    }
    class ViewHolder(val context: Context, override val containerView : View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: MyGrateModel, listener: (MyGrateModel) -> Unit) {
            txtGrateTittle.setText(item.text)
        }
    }
}