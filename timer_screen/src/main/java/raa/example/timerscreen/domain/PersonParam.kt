package raa.example.timerscreen.domain

data class PersonParam(
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val additionalInfo: String = "",
    val placeOfService: String = "",
    var id: Int = UNDEFINED_ID,
    var isSelected: Int = 0,
) {
    companion object {
        private const val UNDEFINED_ID = 0
        const val ERROR_ID = -1

        fun getError(): PersonParam {
            return PersonParam("", 0, 0, "", "", ERROR_ID, 0)
        }
    }
}
