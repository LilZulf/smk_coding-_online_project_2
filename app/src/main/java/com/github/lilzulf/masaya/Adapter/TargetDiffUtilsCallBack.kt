package com.github.lilzulf.masaya.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.lilzulf.masaya.Object.MyTargetModel


class TargetDiffUtilsCallBack(private val oldData: List<MyTargetModel>,
                           private val newData: List<MyTargetModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int,
                                 newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].key == newData[newItemPosition].key
    }

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }
}