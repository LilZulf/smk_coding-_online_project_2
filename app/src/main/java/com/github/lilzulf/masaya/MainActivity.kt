package com.github.lilzulf.masaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.github.lilzulf.masaya.Adapter.ViewPagerAdapter
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val menuTeks = arrayOf("Diary","Pencapaian","Statistik")
    val menuIcons = arrayOf(R.drawable.ic_mood,R.drawable.ic_content,R.drawable.ic_insert_chart)
    var data : SharedPreferences? = null
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(this)
        data = SharedPreferences(applicationContext)
        auth = FirebaseAuth.getInstance()
        viewPager.setAdapter(adapter)
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy {
                tab, position ->
                    tab.text = menuTeks[position]
                    tab.icon = ResourcesCompat.getDrawable(resources,menuIcons[position], null)
        }).attach()
        ic_logout.setOnClickListener {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(this@MainActivity)

            // Set the alert dialog title
            builder.setTitle("Logout")

            // Display a message on alert dialog
            builder.setMessage("Apakah anda yakin untuk keluar?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES"){dialog, which ->
                // Do something when user press the positive button
                data!!.clearSharedPreference()
                auth!!.signOut()
                val i  = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(i)
                finish()
            }

            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel"){_,_ ->
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }
    }
}
