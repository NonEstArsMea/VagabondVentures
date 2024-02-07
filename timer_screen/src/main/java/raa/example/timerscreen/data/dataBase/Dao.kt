package raa.example.timerscreen.data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * FROM persons_profiles")
    fun getPersonsParamList(): List<PersonParamEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPersonParam(personParamEntity: PersonParamEntity)

    @Query("DELETE FROM persons_profiles WHERE id=:delId")
    fun delPersonParam(delId: Int)

    @Query("SELECT * FROM persons_profiles WHERE id=:getId LIMIT 1")
    fun getPersonParam(getId: Int): PersonParamEntity
}