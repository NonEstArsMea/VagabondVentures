package raa.example.timerscreen

fun renameMonth(numberOfMonth: Int): String {
    return when (numberOfMonth + 1) {
        1 -> "Января"
        2 -> "Фераля"
        3 -> "Марта"
        4 -> "Апреля"
        5 -> "Мая"
        6 -> "Июня"
        7 -> "Июля"
        8 -> "Августа"
        9 -> "Сентября"
        10 -> "Октября"
        11 -> "Ноября"
        12 -> "Декабря"
        else -> throw Exception("Unknown number of month")
    }
}