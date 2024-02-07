package raa.example.timerscreen.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons_profiles")
data class PersonParamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val yearEnd: Int?,
    val monthEnd: Int?,
    val dayEnd: Int?
)