package com.geopark.feature_locations_events.presentation.events_menu

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.GeoparkDatabase
import com.geopark.feature_locations_events.data.local.entity.*
import com.geopark.feature_locations_events.domain.use_case.events.EventsUseCase
import com.geopark.feature_locations_events.domain.util.EventCategory
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsMenuViewModel @Inject constructor(
    private val eventsUseCase: EventsUseCase
) : ViewModel() {

    private val _calendarState = mutableStateOf(CalendarPanelState())
    val calendarState: State<CalendarPanelState> = _calendarState

    private val _eventsState = mutableStateOf(EventsState())
    val eventsState: State<EventsState> = _eventsState


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getEventsJob: Job? = null


    private var currentLocation = ".*"

    init {
        getEventsDistinct(EventCategory.ALL)
    }


    private fun getEventsDistinct(category: EventCategory) {
        getEventsJob?.cancel()
        getEventsJob =
            eventsUseCase.getAllEventsDistinct(category).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _eventsState.value = _eventsState.value.copy(
                            events = result.data,
                            category = category,
                            eventsLocations = result.data.distinctBy { it /*it.organizer.name */}
                                .map { it.event.eventTitle /*it.organizer.name*/  },
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _eventsState.value = _eventsState.value.copy(
                            events = result.data,
                            category = category,
                            eventsLocations = result.data.distinctBy { it /*it.organizer.name */}
                                .map { it.event.eventTitle /*it.organizer.name*/  },
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _eventsState.value = _eventsState.value.copy(
                            events = result.data,
                            category = category,
                            eventsLocations = result.data.distinctBy { it /*it.organizer.name */}
                                .map { it.event.eventTitle /*it.organizer.name*/  },
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = result.message
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: EventsMenuEvent) {
        when (event) {
            is EventsMenuEvent.ChangeCategory -> {
                getEventsDistinct(event.category)
            }
        }
    }


}





