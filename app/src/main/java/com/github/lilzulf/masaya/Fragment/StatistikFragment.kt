package com.github.lilzulf.masaya.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.github.lilzulf.masaya.*
import com.github.lilzulf.masaya.Util.tampilToast

import kotlinx.android.synthetic.main.fragment_statistik.*

/**
 * A simple [Fragment] subclass.
 */
class StatistikFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistik, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rl_mood.setOnClickListener {
            val i = Intent(activity!!, StatisticMood::class.java)
            startActivity(i)
            //tampilToast(activity!!,"Coming soon")
        }
        rl_grate.setOnClickListener {
            val i = Intent(activity!!, GratefulHistory::class.java)
            startActivity(i)
        }
    }


}
