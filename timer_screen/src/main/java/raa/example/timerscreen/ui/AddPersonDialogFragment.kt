package raa.example.timerscreen.ui

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.*
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import raa.example.timer_screen.R
import raa.example.timerscreen.renameMonth
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

class AddPersonDialogFragment : DialogFragment() {

    interface DialogListener {
        fun onPositiveClick(year: Int, month: Int, day: Int, text: String)
        fun onNegativeClick()
    }

    private lateinit var listener: DialogListener
    private lateinit var startService: TextView
    private lateinit var editText: EditText
    private lateinit var textColor: ColorStateList
    private lateinit var editDateButton: Button

    private val mainCalendar = Calendar.getInstance()
    private var year = mainCalendar.get(Calendar.YEAR)
    private var month = mainCalendar.get(Calendar.MONTH)
    private var day = mainCalendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_custom_layout, null)

        editText = view.findViewById(R.id.edit_text)
        startService = view.findViewById(R.id.start_service_text)
        editDateButton = view.findViewById(R.id.edit_date_button)

        editDateButton.setOnClickListener {
            openDatePicker()
        }


        val dialog: AlertDialog = builder.setView(view)
            .setTitle("Создание профиля")
            .apply {
                startService.text = "$day ${renameMonth(month)} $year"
            }
            .setPositiveButton("Добавить") { _, _ ->
                val text = editText.text.toString()
                listener.onPositiveClick(year, month, day, text)
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
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)

            startService.text = "$day ${renameMonth(month)} $year"
        }

        // Отображение MaterialDatePicker
        datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
    }
}