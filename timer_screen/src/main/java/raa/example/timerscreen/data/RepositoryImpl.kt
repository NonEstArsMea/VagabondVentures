package raa.example.timerscreen.data

import android.app.Application
import android.util.Log
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import raa.example.timerscreen.Content
import raa.example.timerscreen.State
import raa.example.timerscreen.data.dataBase.Mapper
import raa.example.timerscreen.data.dataBase.PersonsDatabase
import raa.example.timerscreen.domain.PersonParam
import java.util.Calendar
import kotlin.math.abs

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
            it.isSelected = if (it.id == id) 1 else 0
            profileDao.addPersonParam(mapper.mapEntityToDBmodel(it))
        }
    }

    fun addPersonParam(personParam: PersonParam) {
        profileDao.addPersonParam(mapper.mapEntityToDBmodel(personParam))
    }

    fun delPersonParam(delId: Int) {
        profileDao.delPersonParam(delId)
    }

    fun getSelectedPersonsParam(): PersonParam {
        val dbModel = profileDao.getSelectedPersonParam()
        return if (dbModel != null) {
            mapper.mapDBmodelToEntity(dbModel)
        } else {
            PersonParam.getError()
        }
    }


    fun getPersonParamList(): List<PersonParam> {
        return profileDao.getPersonsParamList().map {
            mapper.mapDBmodelToEntity(it)
        }
    }

    fun updatingMainProfile() {

    }

    suspend fun getTime(): Flow<State> = flow {
        val entries = ArrayList<PieEntry>()
        val personParam = getSelectedPersonsParam()
        if (personParam.id != PersonParam.ERROR_ID) {
            val srokSluzby = personParam.endDate - personParam.startDate
            var firstRazn = 0f
            if (srokSluzby != 0.toLong()) {
                firstRazn =
                    ((personParam.startDate - Calendar.getInstance().timeInMillis) / srokSluzby).toFloat()


                Log.e("launch_3", srokSluzby.toString())
                entries.add(PieEntry(firstRazn, "Прошло"))
                entries.add(PieEntry(1 - firstRazn, "Осталось"))
                emit(Content(entries, firstRazn))
                while (true) {
                    val a =
                        (abs((personParam.startDate - Calendar.getInstance().timeInMillis)).toFloat() / srokSluzby)
                    Log.e("launch", a.toString())
                    entries[0] = PieEntry(a)
                    entries[1] = PieEntry(1 - a)
                    emit(Content(entries, (a)))
                    delay(1000)
                }
            }
        }

    }

}