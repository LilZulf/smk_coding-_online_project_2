package com.github.lilzulf.masaya.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lilzulf.masaya.Object.DataGrate
import com.github.lilzulf.masaya.Object.MyGrateModel
import com.github.lilzulf.masaya.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.grate_item1.*
import kotlinx.android.synthetic.main.grate_item1.txtGrateTittle
import kotlinx.android.synthetic.main.grate_item2.*
import kotlinx.android.synthetic.main.target_item.*

class GrateAdapeter1(private val context: Context, private val items:
List<MyGrateModel>, private val listener: (MyGrateModel)-> Unit) :
    RecyclerView.Adapter<GrateAdapeter1.ViewHolder>() {
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
        fun bindItem(item: MyGrateModel, listener: (MyGrateModel) -> Unit) {
           txtGrateTittle.text = item.text
            txtGrateDate.text = item.date
        }
    }
}