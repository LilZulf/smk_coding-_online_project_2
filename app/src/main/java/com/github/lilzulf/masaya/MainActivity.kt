package com.github.lilzulf.masaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.github.lilzulf.masaya.Adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val menuTeks = arrayOf("Diary","Pencapaian","Statistik")
    val menuIcons = arrayOf(R.drawable.ic_diary,R.drawable.ic_list,R.drawable.ic_statistics)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(this)
        viewPager.setAdapter(adapter)
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy {
                tab, position ->
                    tab.text = menuTeks[position]
                    tab.icon = ResourcesCompat.getDrawable(resources,menuIcons[position], null)
        }).attach()
    }
}
