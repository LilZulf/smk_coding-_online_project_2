package com.github.lilzulf.masaya.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lilzulf.masaya.Adapter.RecyclerViewAdapter
import com.github.lilzulf.masaya.Object.TargetObject

import com.github.lilzulf.masaya.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_list_oty.*

/**
 * A simple [Fragment] subclass.
 */
class ListOtyFragment : Fragment() {
    lateinit var listTarget : ArrayList<TargetObject>
    private fun simulasiTarget() {
        listTarget = ArrayList()
        listTarget.add(
            TargetObject("Kerja", "najib")
        )
        listTarget.add(TargetObject("Kuliah", "najib"))
    }
    private fun tampilTarget() {
        rv_listTarget.layoutManager = LinearLayoutManager(activity)
        rv_listTarget.adapter = RecyclerViewAdapter(activity!!, listTarget)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_oty, container, false)
    }override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        simulasiTarget()
        tampilTarget()
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }


}
