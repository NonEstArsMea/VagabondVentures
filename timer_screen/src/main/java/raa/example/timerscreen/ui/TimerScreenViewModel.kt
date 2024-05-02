package raa.example.timerscreen.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import raa.example.timerscreen.Content
import raa.example.timerscreen.State
import raa.example.timerscreen.data.RepositoryImpl
import raa.example.timerscreen.domain.PersonParam
import java.util.Calendar
import kotlin.math.abs

class TimerScreenViewModel(application: Application) : AndroidViewModel(application) {

    var state: Flow<State> = getTime()

    private val repository = RepositoryImpl(application)

    private val entries = ArrayList<PieEntry>()


    private fun getTime(): Flow<State> = channelFlow {
        viewModelScope.launch(Dispatchers.Default) {
            val personParam = repository.getSelectedPersonsParam()
            Log.e("launch_PP", personParam.toString())
            Log.e("param", personParam.toString())
            if (personParam.id != PersonParam.ERROR_ID) {
                val srokSluzby = personParam.endDate - personParam.startDate
                var firstRazn = 0f
                if (srokSluzby != 0.toLong()) {
                    firstRazn =
                        ((personParam.startDate - Calendar.getInstance().timeInMillis) / srokSluzby).toFloat()


                    Log.e("launch_1", personParam.startDate.toString())
                    Log.e("launch_2", Calendar.getInstance().timeInMillis.toString())
                    Log.e("launch_3", srokSluzby.toString())
                    entries.add(PieEntry(firstRazn, "Прошло"))
                    entries.add(PieEntry(1 - firstRazn, "Осталось"))
                    send(Content(entries, firstRazn))
                    while (true) {
                        val a =
                            (abs((personParam.startDate - Calendar.getInstance().timeInMillis)).toFloat() / srokSluzby)
                        Log.e("launch", a.toString())
                        entries[0] = PieEntry(a)
                        entries[1] = PieEntry(1 - a)
                        send(Content(entries, (a)))
                        delay(1000)
                    }
                }
            }


        }

    }

//    private fun getTime(): Flow<State> = flow {
//        var a = 0
//        while (a < 1000){
//            a++
//            emit(Content(a.toFloat()))
//            delay(1000)
//        }
//    }

    fun setNewTime() {
        entries.clear()
        state = getTime()
    }

}