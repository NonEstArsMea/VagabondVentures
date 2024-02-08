package raa.example.timerscreen.data

import android.app.Application
import raa.example.timerscreen.data.dataBase.Mapper
import raa.example.timerscreen.data.dataBase.PersonsDatabase
import raa.example.timerscreen.domain.PersonParam

class RepositoryImpl(
    application: Application
) {

    private val profileDao = PersonsDatabase.getInstance(application).personParamDao()
    private val mapper = Mapper()

    fun editPersonsParam(personParam: PersonParam) {
        profileDao.addPersonParam(mapper.mapEntityToDBmodel(personParam))
    }

    fun setSelectedPersonsParam(id: Int) {
        val listOfPP = getPersonParamList()
        listOfPP.forEach {
            it.isSelected = if(it.id == id) 1 else 0
            profileDao.addPersonParam(mapper.mapEntityToDBmodel(it))
        }
    }

    fun addPersonParam(personParam: PersonParam) {
        profileDao.addPersonParam(mapper.mapEntityToDBmodel(personParam))
    }

    fun delPersonParam(delId: Int) {
        profileDao.delPersonParam(delId)
    }

    fun getPersonsParam(personParamID: Int): PersonParam {
        val dbModel = profileDao.getPersonParam(personParamID)
        return mapper.mapDBmodelToEntity(dbModel)
    }

    fun getPersonParamList(): List<PersonParam> {
        return profileDao.getPersonsParamList().map {
            mapper.mapDBmodelToEntity(it)
        }
    }

    fun updatingMainProfile(){

    }

}