package raa.example.timerscreen.addPersonFragment.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import raa.example.timerscreen.data.RepositoryImpl
import raa.example.timerscreen.domain.PersonParam

class AddPersonFragmentViewModel(application: Application) :
    AndroidViewModel(application) {

    val list = MutableLiveData<List<PersonParam>>()
    private val repository = RepositoryImpl(application)

    fun setParam(personParam: PersonParam) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.addPersonParam(personParam)
            updateList()
        }

    }

    fun updateList() {
        viewModelScope.launch(Dispatchers.Default) {
            list.postValue(repository.getPersonParamList())
        }
    }

    fun setSelected(id: Int){
        viewModelScope.launch(Dispatchers.Default) {
            repository.setSelectedPersonsParam(id)
            updateList()
        }
    }

    fun deleteItem(id: Int){
        viewModelScope.launch(Dispatchers.Default) {
            repository.delPersonParam(id)
            updateList()
        }
    }


}