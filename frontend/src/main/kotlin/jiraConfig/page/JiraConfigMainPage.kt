package jiraConfig.page

import react.*
import react.dom.*


class JiraConfigMainPage : RComponent<Props, State>() {

    override fun RBuilder.render() {
        // TODO the main part of your frontend goes here
        div {
            +"Visit us at "
            a("https://www.linked-planet.com", target = "_blank") {
                +"https://www.linked-planet.com"
            }
        }
    }

}

fun RBuilder.jiraConfigMainPage() =
    child(JiraConfigMainPage::class) {
    }
