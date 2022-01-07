package com.geopark.feature_locations_events.presentation.events_menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.use_case.events.EventsUseCase
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelEvent
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.YearMonth
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

    init {
        getEventsDistinct()
    }



    private fun getEventsDistinct() {
        getEventsJob?.cancel()
        getEventsJob = eventsUseCase.getAllEventsDistinct().onEach { result ->
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

    private fun getEventsForDate() {
        getEventsJob?.cancel()
        val selectedDate = LocalDate.of(calendarState.value.year, calendarState.value.month,calendarState.value.selectedDayOfMonth ?: LocalDate.now().dayOfMonth)
        getEventsJob = eventsUseCase.getEventsForDate(selectedDate).onEach { result ->
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
                _calendarState.value = _calendarState.value.copy(
                    selectedDayOfMonth = calendarEvent.newDayNumber
                )
            }
            is CalendarPanelEvent.ChangeMonth -> {
                val newYear =
                    YearMonth.of(calendarState.value.year, calendarState.value.month)
                        .plusMonths(calendarEvent.monthsToAdd.toLong())
                val newMonth = newYear.month
                _calendarState.value = CalendarPanelState(newYear.year, newMonth)
            }
        }
        getEventsForDate()
    }

}





