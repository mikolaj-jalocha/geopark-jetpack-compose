package com.geoparkcompose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geoparkcompose.data.CardData
import com.geoparkcompose.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: LocationsRepository
) : ViewModel(){

    var loading = mutableStateOf(false)
    val data : MutableState<DataOrException<List<CardData>,Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        getLocations()
    }

    private fun getLocations(){
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getProductsFromFirestore()
            loading.value = false
        }
    }

}