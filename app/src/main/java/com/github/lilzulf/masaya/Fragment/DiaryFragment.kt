package com.github.lilzulf.masaya.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.github.lilzulf.masaya.GratefulActivity
import com.github.lilzulf.masaya.MoodActivity
import com.github.lilzulf.masaya.R
import com.github.lilzulf.masaya.Util.SharedPreferences
import kotlinx.android.synthetic.main.fragment_diary.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment() {
    private var data: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedPreferences(activity!!)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            Toast.makeText(activity!!, "Selamat Pagi", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat pagi, "+data!!.getString("NICKNAME")
            tvGreeting2.text = "Tuliskan dalam hatimu bahwa setiap hari adalah hari terbaik dalam setahun."
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Toast.makeText(activity!!, "Selamat Siang", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat siang, "+data!!.getString("NICKNAME")
            tvGreeting2.text = "Jalani harimu dengan berkah dan cinta."
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Toast.makeText(activity!!, "Selamat Sore", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat sore, "+data!!.getString("NICKNAME")
            tvGreeting2.text = "Ada yang tak tenggelam ketika senja datang, yakni Rasa."
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            Toast.makeText(activity!!, "Selamat malam", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat malam, "+data!!.getString("NICKNAME")
            tvGreeting2.text = "Waktunya padamkan bara setelah lelah bekerja."
        }

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
