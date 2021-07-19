package com.genius.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.genius.test.database.RoomAppDb
import com.genius.test.database.UserEntity


class MainActivityViewModel(app: Application): AndroidViewModel(app) {
    lateinit var allUsers : MutableLiveData<List<UserEntity>>

    init{
        allUsers = MutableLiveData()
        getAllUsers()
    }

    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }

    fun getAllUsers():List<UserEntity> {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()

        allUsers.postValue(list)

        return list!!
    }

    fun insertUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.insertUser(entity)

    }

    fun updateUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.updateUser(entity)

    }

    fun delete(){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.delete()

    }

    fun loadSingle(email:String): UserEntity {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()

        return userDao?.loadSingle(email)!!
    }
}