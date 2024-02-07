package raa.example.timerscreen.data

import android.app.Application
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.coroutineScope
import raa.example.timerscreen.domain.PersonParam

class RepositoryImpl(
    application: Application
) {

    private val profileDao = PersonsDatabase.getInstance(application).personParamDao()
    private val mapper = Mapper()

    fun editPersonsParam(personParam: PersonParam){
        profileDao.addPersonParam(mapper.mapEntityToDBmodel(personParam))
    }

    fun addPersonParam(personParam: PersonParam){
            profileDao.addPersonParam(mapper.mapEntityToDBmodel(personParam))

    }

    fun delPersonParam(delId: Int){
        profileDao.delPersonParam(delId)
    }

    fun getPersonsParam(personParamID: Int): PersonParam{
        val dbModel = profileDao.getPersonParam(personParamID)
        return mapper.mapDBmodelToEntity(dbModel)
    }

    fun getPersonParamList(): List<PersonParam> {
        return profileDao.getPersonsParamList().map {
            mapper.mapDBmodelToEntity(it)
        }
    }

}