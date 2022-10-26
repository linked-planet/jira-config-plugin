package jiraConfig.app

import jiraConfig.reducer.screen
import model.Notification
import reducer.notifications
import redux.*
import kotlin.reflect.KProperty1

data class JiraConfigAppState(
    val notifications: List<Notification> = emptyList(),
    val screen: JiraConfigApplication.Screen = JiraConfigApplication.Screen.Loading
)

fun appReducer() = combinePropertyReducers(
    mapOf(
        JiraConfigAppState::notifications to ::notifications,
        JiraConfigAppState::screen to ::screen
    )
)

fun rootReducer(): Reducer<JiraConfigAppState, RAction> = { state, action ->
    if (action is Clear) {
        appReducer().invoke(JiraConfigAppState(), action)
    } else {
        appReducer().invoke(state, action)
    }
}

private fun <S, A, R> combinePropertyReducers(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}

object Clear : RAction
