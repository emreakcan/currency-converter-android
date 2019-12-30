package com.emre.repo.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(list: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(list: T): Long

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}