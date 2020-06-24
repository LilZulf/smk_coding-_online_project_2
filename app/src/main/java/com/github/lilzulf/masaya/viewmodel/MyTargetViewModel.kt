package com.github.lilzulf.masaya.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.db.MyTargetDatabase
import com.github.lilzulf.masaya.repo.MyTargetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyTargetViewModel:ViewModel() {
    lateinit var repository : MyTargetRepo
    fun init(context: Context) {
        val myTargetDao = MyTargetDatabase.getDatabase(context).myTargetDao()
        repository = MyTargetRepo(myTargetDao)
    }
    fun addData(myTarget: MyTargetModel) =
        viewModelScope . launch (Dispatchers. IO ) {
            repository .insert(myTarget)
        }


}