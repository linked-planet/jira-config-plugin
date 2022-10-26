package jiraConfig.app

import com.linkedplanet.uikit.util.Async
import com.linkedplanet.uikit.wrapper.aui.auiSpinner
import jiraConfig.page.jiraConfigMainPage
import jiraConfig.reducer.JiraConfigHandler
import kotlinx.coroutines.delay
import kotlinx.html.id
import model.Notification
import react.*
import react.dom.*
import react.redux.*
import reducer.NotificationHandler
import redux.*


external interface JiraConfigApplicationStateProps : Props {
    var screen: JiraConfigApplication.Screen
    var notifications: List<Notification>
}

external interface JiraConfigApplicationDispatchProps : Props

external interface JiraConfigApplicationProps : JiraConfigApplicationStateProps, JiraConfigApplicationDispatchProps

val jiraConfigApplication =
    rConnect<
        JiraConfigAppState,
        RAction,
        WrapperAction,
        Props,
        JiraConfigApplicationStateProps,
        JiraConfigApplicationDispatchProps,
        JiraConfigApplicationProps>(
        { state, _ ->
            screen = state.screen
            notifications = state.notifications
        },
        { _, _ -> }
        )(JiraConfigApplication::class.react)

class JiraConfigApplication(props: JiraConfigApplicationStateProps) :
    RComponent<JiraConfigApplicationStateProps, State>(props) {

    override fun componentDidMount() {
        // load initial data and switch to main screen once loaded
        Async.complete(
            taskName = "load-data",
            taskFun = {
                // TODO usually perform a rest call to load initial app data
                delay(1000)
            },
            completeFun = { _ ->
                // TODO set the data as app state via the handler
                JiraConfigHandler.setScreen(Screen.Main)
            },
            // TODO might want to use a more descriptive error message
            catchFun = { error -> NotificationHandler.show(error, "Loading initial data") }
        )
    }

    override fun RBuilder.render() {
        renderMainComponent()
        renderNotifications()
    }

    private fun RBuilder.renderNotifications() {
        if (props.notifications.isNotEmpty()) {
            // TODO define how to render notifications
        }
    }

    private fun RBuilder.renderMainComponent() {
        val screen = props.screen
        div {
            attrs.key = "main-component"
            attrs.id = "main-component"
            when (screen) {
                is Screen.Loading ->
                    if (props.notifications.isEmpty()) {
                        div("util-align-loading-spinner") {
                            auiSpinner {
                                attrs["size"] = "medium"
                            }
                        }
                    }
                is Screen.Main ->
                    // TODO you can pass data into the page function using the screen object as DTO
                    jiraConfigMainPage()
            }
        }
    }

    sealed class Screen {
        object Loading : Screen()
        object Main : Screen()
    }

    companion object {
        val appStore = createStore<JiraConfigAppState, RAction, dynamic>(
            rootReducer(), JiraConfigAppState(), compose(
                rEnhancer(),
                js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
            )
        )
    }

}
