package raa.example.timerscreen.data

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
            dayEnd=param.dayEnd
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
            dayEnd=param.dayEnd
        )
    }

    fun mapListDBModekToListEntity(list:List<PersonParamEntity>)=list.map {
        mapDBmodelToEntity(it)
    }
}