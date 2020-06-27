package com.github.lilzulf.masaya.dao

import com.github.lilzulf.masaya.Object.StateModel

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.lilzulf.masaya.Object.MyTargetModel
@Dao interface StateDao {

    @Query( "SELECT   *   from   my_state" )
    fun getAll(): LiveData<List<StateModel>>

}