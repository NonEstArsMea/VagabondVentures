package raa.example.timerscreen.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import raa.example.timerscreen.Content
import raa.example.timerscreen.State

class TimerScreenViewModel : ViewModel() {

    val state: Flow<State> = getTime()


    private fun getTime(): Flow<State> = flow {
        var a = 0
        while (a < 1000){
            a++
            emit(Content(a.toFloat()))
            delay(1000)
        }
    }

}