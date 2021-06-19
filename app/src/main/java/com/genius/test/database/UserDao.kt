package com.genius.test.database

import androidx.room.*


@Dao
interface UserDao {

    @Query("SELECT * from userinfo  ORDER BY name DESC")
    fun getAllUserInfo(): List<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity?)

    @Delete
    fun deleteUser(user: UserEntity?)

    @Delete
    fun deleteUsers(user: List<UserEntity>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserEntity?)


    @Query("SELECT * FROM userinfo WHERE email=:email ")
    fun loadSingle(email: String): UserEntity?

    @Query("DELETE FROM userinfo")
   fun delete();
}

