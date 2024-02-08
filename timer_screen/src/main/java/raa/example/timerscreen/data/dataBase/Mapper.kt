package raa.example.timerscreen.data.dataBase

import android.util.Log
import raa.example.timerscreen.domain.PersonParam

class Mapper {

    fun mapEntityToDBmodel(param: PersonParam): PersonParamEntity {
        return PersonParamEntity(
            id=param.id,
            name=param.name,
            year=param.year,
            month=param.month,
            day=param.day,
            yearEnd=param.yearEnd,
            monthEnd=param.monthEnd,
            dayEnd=param.dayEnd,
            isSelected = param.isSelected
        )
    }

    fun mapDBmodelToEntity(param: PersonParamEntity): PersonParam {
        return PersonParam(
            id=param.id,
            name=param.name,
            year=param.year,
            month=param.month,
            day=param.day,
            yearEnd=param.yearEnd,
            monthEnd=param.monthEnd,
            dayEnd=param.dayEnd,
            isSelected = param.isSelected
        )
    }

    fun mapListDBModelToListEntity(list:List<PersonParamEntity>)=list.map {
        mapDBmodelToEntity(it)
    }

    fun mapListEntityToListDBModel(list:List<PersonParam>)=list.map {
        mapEntityToDBmodel(it)
    }
}