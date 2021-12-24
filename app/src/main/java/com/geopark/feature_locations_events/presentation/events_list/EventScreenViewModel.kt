package com.geopark.feature_locations_events.presentation.events_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.use_case.events.EventsUseCase
import com.geopark.feature_locations_events.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventsUseCase: EventsUseCase
) : ViewModel() {

    private val _calendarPanelState = mutableStateOf(CalendarPanelState())
    val calendarPanelState: State<CalendarPanelState> = _calendarPanelState

    private val _eventsState = mutableStateOf(EventsState())
    val eventsState: State<EventsState> = _eventsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getEventsJob: Job? = null

    init {
        Log.d("EVENTS_VM", "get event's  been called")
        getEvents()
    }


    private fun getEvents() {
        getEventsJob?.cancel()
        getEventsJob = eventsUseCase.getEvents().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eventsState.value = _eventsState.value.copy(
                        events = result.data,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _eventsState.value = _eventsState.value.copy(
                        events = result.data,
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _eventsState.value = _eventsState.value.copy(
                        events = result.data,
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


    fun onCalendarEvent(calendarEvent: CalendarPanelEvent) {
        when (calendarEvent) {
            is CalendarPanelEvent.ChangeDay -> {
                _calendarPanelState.value = _calendarPanelState.value.copy(
                    selectedDayOfMonth = calendarEvent.newDayNumber
                )
            }
            is CalendarPanelEvent.ChangeMonth -> {
                val newYear =
                    YearMonth.of(calendarPanelState.value.year, calendarPanelState.value.month)
                        .plusMonths(calendarEvent.monthsToAdd.toLong())
                val newMonth = newYear.month
                _calendarPanelState.value = CalendarPanelState(newYear.year, newMonth)

            }
        }
    }

}





