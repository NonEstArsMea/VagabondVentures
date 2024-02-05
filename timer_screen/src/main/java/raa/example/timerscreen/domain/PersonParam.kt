package raa.example.timerscreen.domain

data class PersonParam(
    val name: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val yearEnd: Int? = null,
    val monthEnd: Int? = null,
    val dayEnd: Int? = null,
    var id: Int = UNDEFINED_ID
){
    companion object{
        private const val UNDEFINED_ID = -1
    }
}
