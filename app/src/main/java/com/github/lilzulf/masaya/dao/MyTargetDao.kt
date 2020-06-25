package com.github.lilzulf.masaya.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.lilzulf.masaya.Object.MyTargetModel

interface MyTargetDao {

    @Query( "SELECT   *   from   my_target" )
    fun getAllMyFriend(): LiveData<List<MyTargetModel>>

    @Insert( onConflict   =
    OnConflictStrategy . REPLACE )
    suspend   fun insert(myFriend:   MyTargetModel)
    @Insert ( onConflict   =
    OnConflictStrategy . REPLACE )
    suspend   fun
            insertAll(myFriends:   List<MyTargetModel>)
    @Update( onConflict   =
    OnConflictStrategy . REPLACE )
    suspend   fun update(myFriend:   MyTargetModel)
    @Delete()
    suspend   fun delete(myFriend:   MyTargetModel)
    @Query ( "DELETE   FROM   my_target" )
    suspend   fun deleteAll()
    @Query("SELECT * FROM my_target WHERE date = :date")
    fun getByDate(date: String) : List<MyTargetModel>
}