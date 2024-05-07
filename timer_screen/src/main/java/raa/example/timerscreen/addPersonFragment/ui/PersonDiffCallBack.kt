package raa.example.timerscreen.addPersonFragment.ui

import androidx.recyclerview.widget.DiffUtil
import raa.example.timerscreen.domain.PersonParam

class PersonDiffCallBack: DiffUtil.ItemCallback<PersonParam>() {
    override fun areItemsTheSame(oldItem: PersonParam, newItem: PersonParam): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PersonParam, newItem: PersonParam): Boolean {
        return  oldItem == newItem
    }

}