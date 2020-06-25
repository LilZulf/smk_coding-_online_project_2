package com.github.lilzulf.masaya.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.lilzulf.masaya.EditTarget
import com.github.lilzulf.masaya.Object.DataItem
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.R
import com.github.lilzulf.masaya.TargetUpdateActivity
import com.github.lilzulf.masaya.Util.tampilToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.target_item.*

class TargetAdapter2(private val context: Context, private val items:
List<MyTargetModel>, var listener: (MyTargetModel)-> Unit) :
    RecyclerView.Adapter<TargetAdapter2.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            context, LayoutInflater.from(context).inflate(
                R.layout.target_item,
                parent, false
            )
        )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener)
    }

    class ViewHolder(val context: Context, override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: MyTargetModel, listener: (MyTargetModel) -> Unit) {
            txtTargetTittle.text = item.tittle

            lateinit var ref: DatabaseReference
            lateinit var auth: FirebaseAuth
            rlItem.setOnClickListener {
               // tampilToast(context, txtTargetTittle.text.toString())
                val action = arrayOf("Update", "Delete")
                val alert = AlertDialog.Builder(context)
                alert.setTitle(item.tittle)
                alert.setItems(action) { dialog, i ->
                    when (i) {
                        0 -> {
                            val bundle = Bundle()
                            bundle.putString("title", item.tittle)
                            bundle.putString("year",item.date)
                            bundle.putString("id",item.key)
                            val i = Intent(context, TargetUpdateActivity::class.java)
                            i.putExtras(bundle)
                            context.startActivity(i)
                        }
                        1 -> {
                            auth = FirebaseAuth.getInstance()
                            ref = FirebaseDatabase.getInstance().getReference()
                            val getUserID: String =
                                auth?.getCurrentUser()?.getUid().toString()
                            if (ref != null) {
                                ref.child(getUserID)
                                    .child("Target")
                                    .child(item.key.toString())
                                    .removeValue()
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            context, "Data Berhasil Dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    }
//                val i = Intent(context,EditTarget::class.java)
//                context.startActivity(i)
                }
                alert.create()
                alert.show()
                true
            }
        }
    }
}