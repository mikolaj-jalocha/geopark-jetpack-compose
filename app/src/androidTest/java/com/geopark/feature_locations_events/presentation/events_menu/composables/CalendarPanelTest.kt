package com.geopark.feature_locations_events.presentation.events_menu.composables

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.geopark.feature_locations_events.presentation.events_list.composables.CalendarPanel
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth
import kotlin.random.Random

class CalendarPanelTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val state = mutableStateOf(CalendarPanelState())
    private val currentDay = LocalDate.now().dayOfMonth

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CalendarPanel(state = state.value, onDayChange = {
                onDayChange(it)
            }, onMonthChange = {
                onMonthChange(it)
            })
        }
    }

    @Test
    fun displayedDateIsSameAsInState() {
        composeTestRule.onNodeWithContentDescription("Selected month")
            .assertTextEquals("${state.value.month}, ${state.value.year}")

        composeTestRule.onNodeWithContentDescription("Next month").assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithContentDescription("Selected month")
            .assertTextEquals("${state.value.month}, ${state.value.year}")

        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithContentDescription("Selected month")
            .assertTextEquals("${state.value.month}, ${state.value.year}")

        while (state.value.year == LocalDate.now().year) {
            composeTestRule.onNodeWithContentDescription("Next month").assertIsDisplayed()
                .performClick()
        }

        composeTestRule.onNodeWithContentDescription("Selected month")
            .assertTextEquals("${Month.JANUARY}, ${LocalDate.now().year + 1}")
    }

    @Test
    fun goingToPastIsNotPossible() {

        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed()
            .assertIsNotEnabled()

        composeTestRule.onNodeWithContentDescription("Next month").assertIsDisplayed()
            .performClick()


        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed()
            .assertIsEnabled().performClick()
        composeTestRule.onNodeWithContentDescription("Previous month").assertIsDisplayed()
            .assertIsNotEnabled()

        //going exactly one year ahead and previous month of that time  should be possible to see

        do {
            composeTestRule.onNodeWithContentDescription("Next month").performClick()
        } while (state.value.month != LocalDate.now().month)

        composeTestRule.onNodeWithContentDescription("Previous month").assertIsEnabled()

    }


    @Test
    fun currentDayIsSelectedWhenStateIsInCurrentDate() {

        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay"))
            .assertIsDisplayed().assertIsSelected()
    }

    @Test
    fun currentDayIsSelectedWhenGoingBackFromNextMonth() {

        composeTestRule.onNodeWithContentDescription("Next month").performClick()

        composeTestRule.onNodeWithContentDescription("Previous month").performClick()

        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay"))
            .assertIsDisplayed().assertIsSelected()

    }

    @Test
    fun noDayIsSelectedWhenStateIsNotInCurrentMonth() {

        composeTestRule.onNodeWithContentDescription("Next month").performClick()
        composeTestRule.onNode(hasContentDescription("Day number") and hasText("1"))
            .assertIsDisplayed().assertIsNotSelected()


        composeTestRule.onNodeWithContentDescription("Select day")
            .performScrollToIndex(currentDay - 1)
        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay"))
            .assertIsDisplayed().assertIsNotSelected()

    }

    @Test
    fun noDayIsSelectedWhenStateIsNotInCurrentYear() {
        do {
            composeTestRule.onNodeWithContentDescription("Next month").performClick()
        } while (state.value.month != LocalDate.now().month)

        composeTestRule.onNodeWithContentDescription("Select day")
            .performScrollToIndex(currentDay - 1)
        composeTestRule.onNode(hasContentDescription("Day number") and hasText("$currentDay"))
            .assertIsDisplayed().assertIsNotSelected()

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