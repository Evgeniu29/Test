package com.genius.test.LoginViewModel

import com.genius.test.model.LoginTableModel
import com.genius.test.repository.LoginRepository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {

    var liveDataLogin: LiveData<LoginTableModel>? = null

    fun insertData(context: Context, username: String, password: String) {
        LoginRepository.insertData(context, username, password)
    }

    fun getLoginDetails(context: Context, username: String) : LiveData<LoginTableModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }

}