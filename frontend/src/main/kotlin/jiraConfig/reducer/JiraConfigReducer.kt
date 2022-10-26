package jiraConfig.reducer

import jiraConfig.app.JiraConfigApplication
import jiraConfig.app.JiraConfigApplication.Companion.appStore
import redux.RAction

// ------------------------------------------------------------------------------------------
// ACTIONS
// ------------------------------------------------------------------------------------------
class SetScreenAction(val screen: JiraConfigApplication.Screen) : RAction

// ------------------------------------------------------------------------------------------
// REDUCER
// ------------------------------------------------------------------------------------------
fun screen(
    state: JiraConfigApplication.Screen = JiraConfigApplication.Screen.Loading,
    action: RAction
): JiraConfigApplication.Screen =
    when (action) {
        is SetScreenAction -> action.screen
        else -> state
    }

// ------------------------------------------------------------------------------------------
// HANDLER
// ------------------------------------------------------------------------------------------
object JiraConfigHandler {
    fun setScreen(screen: JiraConfigApplication.Screen) {
        appStore.dispatch(SetScreenAction(screen))
    }
}


