package raa.example.timerscreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import raa.example.timer_screen.R
import raa.example.timerscreen.data.PersonParam
import raa.example.timerscreen.renameMonth
import java.time.Year

class RecycleViewAdapter :
    ListAdapter<PersonParam, RecycleViewAdapter.RVOnSearchFragmentViewHolder>(
        PersonDiffCallBack()
    ) {
    class RVOnSearchFragmentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)
        val date = view.findViewById<TextView>(R.id.date)

        fun bind(text: String, year: Int, month: Int, day: Int) {
            name.text = text
            date.text = "$day ${renameMonth(month)} $year"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVOnSearchFragmentViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.person_view_holder, parent, false)

        return RVOnSearchFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVOnSearchFragmentViewHolder, position: Int) {
        currentList[position].apply {
            holder.bind(name, year, month, day)
        }

    }
}
