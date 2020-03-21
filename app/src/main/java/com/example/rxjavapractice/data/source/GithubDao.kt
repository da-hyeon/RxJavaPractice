package com.example.rxjavapractice.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rxjavapractice.entity.GithubRepo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubDao {
    @Query("SELECT * FROM githubrepo")
    fun selectAll(): Single<List<GithubRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: GithubRepo): Completable
}
