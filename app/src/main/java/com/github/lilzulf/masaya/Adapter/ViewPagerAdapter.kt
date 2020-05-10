package com.github.lilzulf.masaya.Adapter

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.lilzulf.masaya.Fragment.DiaryFragment
import com.github.lilzulf.masaya.Fragment.ListOtyFragment
import com.github.lilzulf.masaya.Fragment.StatistikFragment


class ViewPagerAdapter(fragmentActivity : FragmentActivity):FragmentStateAdapter(fragmentActivity){

    private val JumlahMenu = 3

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {return DiaryFragment()}
            1 -> {return ListOtyFragment()}
            2 -> {return StatistikFragment()}
            else -> {
                return DiaryFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return  JumlahMenu
    }

}