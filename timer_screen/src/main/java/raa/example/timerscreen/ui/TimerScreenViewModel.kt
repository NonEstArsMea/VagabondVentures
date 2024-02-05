package raa.example.timerscreen.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import raa.example.timerscreen.Content
import raa.example.timerscreen.State

class TimerScreenViewModel : ViewModel() {

    val state: Flow<State> = getTime()

    private val entries = ArrayList<PieEntry>()


    private fun getTime(): Flow<State> = flow {
        var a = 0

        entries.add(PieEntry(1.00005f, "Прошло"))
        entries.add(PieEntry(999.00005f, "Осталось"))

        while (a < 1000){
            a++
            Log.e("art", a.toString())
            entries[0] = PieEntry(a.toFloat())
            entries[1] = PieEntry(entries[1].value - 1)
            emit(Content(entries, (a.toFloat() + 0.001f)))
            delay(1000)
        }
    }

}