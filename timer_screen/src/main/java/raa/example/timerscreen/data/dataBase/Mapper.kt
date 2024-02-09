package raa.example.timerscreen.data.dataBase

import android.util.Log
import raa.example.timerscreen.domain.PersonParam

class Mapper {

    fun mapEntityToDBmodel(param: PersonParam): PersonParamEntity {
        return PersonParamEntity(
            id=param.id,
            name=param.name,
            startDate = param.startDate,
            endDate = param.endDate,
            additionalInfo = param.additionalInfo,
            placeOfService = param.placeOfService,
            isSelected = param.isSelected
        )
    }

    fun mapDBmodelToEntity(param: PersonParamEntity): PersonParam {
        return PersonParam(
            id=param.id,
            name=param.name,
            startDate = param.startDate,
            endDate = param.endDate,
            additionalInfo = param.additionalInfo,
            placeOfService = param.placeOfService,
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