package com.geopark.feature_locations_events.domain.use_case.events

data class EventsUseCase(
    val getAllEventsFlowUseCase: GetAllEventsFlowUseCase,
    val getEventsForCategoryUseCase: GetEventsForCategoryUseCase,
    val getEventsForDateAndCategoryUseCase: GetEventsForDateAndCategoryUseCase
)