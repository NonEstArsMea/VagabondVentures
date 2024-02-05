package raa.example.timerscreen

import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieEntry

sealed class State

data object Loading: State()

class Content(val entry: ArrayList<PieEntry>, val a:Float): State()