package raa.example.timerscreen.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raa.example.res.R.color.col_second_background_color
import raa.example.timer_screen.R
import raa.example.timerscreen.domain.PersonParam
import raa.example.timerscreen.renameMonth

class RecycleViewAdapter :
    ListAdapter<PersonParam, RecycleViewAdapter.RVOnSearchFragmentViewHolder>(
        PersonDiffCallBack()
    ) {

    var onClickListener: ((PersonParam) -> (Unit))? = null

    class RVOnSearchFragmentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)
        val date = view.findViewById<TextView>(R.id.date)
        val number = view.findViewById<TextView>(R.id.number)
        val endDate = view.findViewById<TextView>(R.id.end_date)

        fun bind(text: String, dateStart: String, dateEnd: String, position: Int) {
            name.text = text
            date.text = "$dateStart"
            endDate.text = "$dateEnd"
            number.text = position.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVOnSearchFragmentViewHolder {

        val layout = when (viewType) {
            DESABLET_TYPE -> R.layout.person_view_holder
            ENEBLED_TYPE -> R.layout.second_person_view_holder

            else -> throw java.lang.RuntimeException("Unknown type : $viewType ")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return RVOnSearchFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVOnSearchFragmentViewHolder, position: Int) {
        currentList[position].apply {
            val dateStart = "$day ${renameMonth(month)} $year"
            val dateEnd = if (monthEnd != null) "$dayEnd ${renameMonth(monthEnd)} $yearEnd" else ""
            holder.bind(name, dateStart, dateEnd, position + 1)
        }

        holder.view.setOnClickListener {
            onClickListener?.invoke(currentList[position])
        }

    }

    override fun getItemViewType(position: Int): Int {

        return if ((currentList[position].isSelected == 1) or (currentList.size == 1)) ENEBLED_TYPE else DESABLET_TYPE
    }

    companion object {
        const val ENEBLED_TYPE = 1
        const val DESABLET_TYPE = 0
    }
}
