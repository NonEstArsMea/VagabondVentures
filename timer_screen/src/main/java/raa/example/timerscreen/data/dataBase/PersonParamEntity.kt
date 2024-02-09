package raa.example.timerscreen.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons_profiles")
data class PersonParamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val additionalInfo: String,
    val placeOfService: String,
    val isSelected: Int,
)