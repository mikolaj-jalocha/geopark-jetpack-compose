package com.geopark.feature_locations_events.presentation.events_menu.composables

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.geopark.feature_locations_events.presentation.events_list.composables.CalendarPanel
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelState
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import kotlin.random.Random

class CalendarPanelTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val state = mutableStateOf(CalendarPanelState())



    @Test
    fun goingToPastIsNotPossible(){

        resetCalendarState()
        composeTestRule.setContent {
            CalendarPanel(state = state.value, onDayChange = {
                onDayChange(it)
            }, onMonthChange = {
                onMonthChange(it)
            })
        }

        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed().assertIsNotEnabled()

        composeTestRule.onNodeWithContentDescription("Next month").assertIsDisplayed().performClick()


        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed().assertIsEnabled().performClick()
        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed().assertIsNotEnabled()

        //going exactly one year ahead and previous month of that time  should be possible to see

        do{
            composeTestRule.onNodeWithContentDescription("Next month").performClick()
        }while (state.value.month != LocalDate.now().month)

        composeTestRule.onNodeWithContentDescription("Previous month").assertIsEnabled()

    }



    @Test
    fun actualAndNextDayOfMonthCheck(){
        resetCalendarState()
        composeTestRule.setContent {
            CalendarPanel(state = state.value, onDayChange = {
                onDayChange(it)
            }, onMonthChange = {
                onMonthChange(it)
            })
        }

        val currentDay = LocalDateTime.now().dayOfMonth

        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay")).assertIsDisplayed().assertIsSelected()


        composeTestRule.onNodeWithContentDescription("Next month").performClick()
        composeTestRule.onNode(hasContentDescription("Day number") and hasText("1")).assertIsDisplayed().assertIsNotSelected()


        composeTestRule.onNodeWithContentDescription("Select day").performScrollToIndex(currentDay-1)
        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay")).assertIsDisplayed().assertIsNotSelected()


        //after switching to actual month the actual day should be displayed and selected

        composeTestRule.onNodeWithContentDescription("Previous month").performClick()
        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay")).assertIsDisplayed().assertIsSelected()
    }




    private fun resetCalendarState(){
        state.value = CalendarPanelState()
    }

    private fun onDayChange(newDayNumber: Int) {
        state.value = state.value.copy(
            selectedDayOfMonth = newDayNumber
        )
    }

    private fun onMonthChange(monthsToAdd: Int) {
        val newYear =
            YearMonth.of(state.value.year, state.value.month)
                .plusMonths(monthsToAdd.toLong())
        val newMonth = newYear.month
        state.value = CalendarPanelState(newYear.year, newMonth)
    }


}