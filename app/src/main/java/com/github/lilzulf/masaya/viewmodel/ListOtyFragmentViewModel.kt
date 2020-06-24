package com.github.lilzulf.masaya.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.db.MyTargetDatabase
import com.github.lilzulf.masaya.repo.MyTargetRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListOtyFragmentViewModel() : ViewModel() {
    lateinit var repository: MyTargetRepo
    lateinit var allMyTarget: LiveData<List<MyTargetModel>>
    fun init(context: Context) {
        val myTargetDao = MyTargetDatabase.getDatabase(context).myTargetDao()
        repository = MyTargetRepo(myTargetDao)
        allMyTarget = repository.allMyFriend
    }
    fun delete(myTarget: MyTargetModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(myTarget)
    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertAll(myTarget: List<MyTargetModel>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
            repository.insertAll(myTarget)
        }

}