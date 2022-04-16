package com.geopark.feature_locations_events.presentation.events_menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.use_case.events.EventsUseCase
import com.geopark.feature_locations_events.domain.util.EventCategory
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    private var getEventsJobFlow: Job? = null


    init {
        getEventsForDate(
            eventCategory = eventsState.value.category,
            calendarState.value.getSelectedDate()
        )
    }


    fun onEvent(event: EventsMenuEvent) {
        when (event) {
            is EventsMenuEvent.ChangeCategory -> {
                if (event.category == EventCategory.ALL) {
                    getEventsForDate(event.category, calendarState.value.getSelectedDate())

                } else {
                    getEventsForDate(event.category, calendarState.value.getSelectedDate())
                }
            }
            is EventsMenuEvent.ChangeDay -> {
                _calendarState.value = _calendarState.value.copy(
                    selectedDayOfMonth = event.newDayNumber
                )
                getEventsForDate(
                    eventCategory = _eventsState.value.category,
                    calendarState.value.getSelectedDate()
                )

            }
            is EventsMenuEvent.ChangeMonth -> {
                val newYear =
                    YearMonth.of(calendarState.value.year, calendarState.value.month)
                        .plusMonths(event.monthsToAdd.toLong())
                val newMonth = newYear.month
                _calendarState.value = CalendarPanelState(newYear.year, newMonth)
                getEventsForDate(_eventsState.value.category, calendarState.value.getSelectedDate())
            }

            is EventsMenuEvent.GetUpcomingEvents -> {
                getUpcomingEvents(eventsState.value.category)
            }

        }
    }


    private fun getUpcomingEvents(eventCategory: EventCategory) {
        getEventsJobFlow?.cancel()
        getEventsJobFlow = viewModelScope.launch {
            eventsUseCase.getEventsForCategoryUseCase(eventCategory).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _eventsState.value = _eventsState.value.copy(
                            upcomingEvents = true,
                            events = result.data,
                            category = eventCategory,
                            isLoading = false,

                            )
                    }
                    is Resource.Loading -> {
                        _eventsState.value = _eventsState.value.copy(
                            upcomingEvents = true,
                            events = result.data,
                            category = eventCategory,
                            isLoading = true
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun getEventsForDate(
        eventCategory: EventCategory,
        date: LocalDate
    ) {
        getEventsJobFlow?.cancel()
        _eventsState.value = _eventsState.value.copy(
            upcomingEvents = false,
            isLoading = true
        )
        getEventsJobFlow = viewModelScope.launch {
            eventsUseCase.getEventsForDateAndCategoryUseCase(eventCategory, date).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _eventsState.value = _eventsState.value.copy(
                            events = result.data,
                            category = eventCategory,
                            isLoading = false,

                            )
                    }
                    is Resource.Loading -> {
                        _eventsState.value = _eventsState.value.copy(
                            events = result.data,
                            category = eventCategory,
                            isLoading = true
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    }

}





