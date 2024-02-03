package raa.example.timerscreen

sealed class State

data object Loading: State()

class Content(val count: Float): State()