package com.genius.test.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userinfo")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "image")
    val image: String

)
