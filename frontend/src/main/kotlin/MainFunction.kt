import org.w3c.dom.Element
import jiraConfig.app.*
import react.dom.*
import react.redux.provider

@OptIn(ExperimentalJsExport::class)
@JsExport
fun startJiraConfig(container: Element) {
    kotlinext.js.require("base.scss")
    kotlinext.js.require("util.scss")
    kotlinext.js.require("@fortawesome/fontawesome-free/js/all.js")

    kotlinext.js.require("app-jira-config.scss")

    render(container) {
        provider(JiraConfigApplication.appStore) {
            jiraConfigApplication {}
        }
    }
}


@OptIn(ExperimentalJsExport::class)
@JsExport
fun stopJiraConfig(container: Element) {
    JiraConfigApplication.appStore.dispatch(Clear)
    unmountComponentAtNode(container)
}
