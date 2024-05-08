package raa.example.timerscreen.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    private var _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state


    private val repository = RepositoryImpl(application,viewModelScope)

    var flowJob : Job = viewModelScope.launch {  }



    private val entries = ArrayList<PieEntry>()

    fun loadData() {
        flowJob = viewModelScope.launch(Dispatchers.IO) {
            Log.e("launch", "start In ViewModel")
            repository.getTime()
                .collect {
                    _state.postValue(it)
                }
        }
    }

    fun getNewData(){
        flowJob.cancel()
        loadData()
    }



//    private fun getTime(): Flow<State> = flow {
//        var a = 0
//        while (a < 1000){
//            a++
//            emit(Content(a.toFloat()))
//            delay(1000)
//        }
//    }


}