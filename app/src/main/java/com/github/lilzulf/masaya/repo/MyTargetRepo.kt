package com.github.lilzulf.masaya.repo

import androidx.lifecycle.LiveData
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.dao.MyTargetDao

class MyTargetRepo(private val myFriendDao: MyTargetDao) {
    // Room executes all queries on a separate thread.
// Observed LiveData will notify the observer when the data has changed.
    val allMyTarget: LiveData<List<MyTargetModel>> =
        myFriendDao.getAllMyFriend()
    suspend fun insert(myFriend: MyTargetModel) {
        myFriendDao.insert(myFriend)
    }
    suspend fun insertAll(myFriends: List<MyTargetModel>) {
        myFriendDao.insertAll(myFriends)
    }
    suspend fun deleteAll() {
        myFriendDao.deleteAll()
    }
    suspend fun update(myFriend: MyTargetModel) {
        myFriendDao.update(myFriend)
    }
    suspend fun delete(myFriend: MyTargetModel) {
        myFriendDao.delete(myFriend)
    }
}
