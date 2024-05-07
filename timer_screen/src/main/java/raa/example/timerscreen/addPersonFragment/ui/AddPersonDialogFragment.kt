package raa.example.timerscreen.addPersonFragment.ui

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.*
import androidx.core.util.Pair
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import raa.example.timer_screen.R
import raa.example.timerscreen.domain.PersonParam
import raa.example.timerscreen.renameMonth
import java.util.Calendar

class AddPersonDialogFragment : DialogFragment() {

    interface DialogListener {
        fun onPositiveClick(param: PersonParam)
        fun onNegativeClick()
    }

    private lateinit var listener: DialogListener
    private lateinit var startService: TextView
    private lateinit var endService: TextView
    private lateinit var editText: EditText
    private lateinit var textColor: ColorStateList
    private lateinit var editDateButton: Button

    private var startServiceTime: Long = 0
    private var endServiceTime: Long = 0

    // Диалог для создания юзера
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_custom_layout, null)


        initView(view)

        editDateButton.setOnClickListener {
            openDatePicker()
        }


        val dialog: AlertDialog = builder.setView(view)
            .setTitle("Создание профиля")
            .apply {
                startService.text = convertTime(getTimePair().first)
                endService.text = convertTime(getTimePair().second)
            }
            .setPositiveButton("Добавить") { _, _ ->
                val person = PersonParam(
                    editText.text.toString(),
                    getTimePair().first,
                    getTimePair().second,

                )
                Log.e("tag", person.toString())
                listener.onPositiveClick(person)
            }
            .setNegativeButton("Отменить") { _, _ ->
                listener.onNegativeClick()
            }
            .create()

        dialog.show()

        dialog.getButton(BUTTON_POSITIVE).apply {
            isClickable = false
            textColor = textColors
            setTextColor(Color.GRAY)
        }


        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Проверка, нужно ли разблокировать кнопку
                dialog.getButton(BUTTON_POSITIVE).setClicable(s)
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dialog.getButton(BUTTON_POSITIVE).setClicable(s)
            }
        })


        return dialog
    }


    fun setDialogListener(listener: DialogListener) {
        this.listener = listener
    }

    private fun Button.setClicable(s: CharSequence?) {
        if (s?.isNotBlank() == true) {
            this.isClickable = true
            this.setTextColor(textColor)
        } else {
            this.isClickable = false
            this.setTextColor(Color.GRAY)
        }

    }

    private fun openDatePicker() {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Выберите дату")
            .setSelection(getTimePair())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            startService.text = convertTime(selection.first)
            endService.text = convertTime(selection.second)

            startServiceTime = selection.first
            endServiceTime = selection.second
        }

        datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
    }

    private fun convertTime(time: Long): String {
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = time
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$day ${renameMonth(month)} $year"
    }

    private fun getTimePair(): Pair<Long, Long> {
        val firstTime = Calendar.getInstance().timeInMillis
        val calendarSecond = Calendar.getInstance()
        calendarSecond.add(Calendar.YEAR, 1)
        val seconTime = calendarSecond.timeInMillis
        calendarSecond.add(Calendar.YEAR, -1)
        return (Pair(firstTime, seconTime))
    }

    private fun initView(view: View){
        editText = view.findViewById(R.id.edit_text)
        startService = view.findViewById(R.id.start_service_text)
        endService = view.findViewById(R.id.end_service_text)
        editDateButton = view.findViewById(R.id.edit_date_button)
    }
}