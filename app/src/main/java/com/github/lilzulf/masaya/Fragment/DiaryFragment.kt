package com.github.lilzulf.masaya.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.github.lilzulf.masaya.GratefulActivity
import com.github.lilzulf.masaya.MoodActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_diary.*


import com.github.lilzulf.masaya.R

/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rl_mood.setOnClickListener {
            val i = Intent(activity!!, MoodActivity::class.java)
            startActivity(i)
        }
        rl_grate.setOnClickListener {
            val i = Intent(activity!!, GratefulActivity::class.java)
            startActivity(i)
        }
    }


}
