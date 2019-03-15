package app

import home.Home
import kotlinx.html.ButtonType
import kotlinx.html.id
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*
import react.router.dom.browserRouter
import react.router.dom.navLink
import react.router.dom.route
import react.router.dom.switch
import search.Search

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        browserRouter {
            div {
                nav("navbar navbar-expand-lg navbar-light bg-light") {
                    button(classes = "navbar-toggler", type = ButtonType.button) {
                        attrs["data-toggle"] = "collapse"
                        attrs["data-target"] = "#navbarSupportedContent"
                        attrs["aria-controls"] = "navbarSupportedContent"
                        attrs["aria-expanded"] = "false"
                        attrs["aria-label"] = "Toggle navigation"
                        span("navbar-toggler-icon") { }
                    }
                    div("collapse navbar-collapse") {
                        attrs {
                            id = "navbarSupportedContent"
                        }
                        ul("navbar-nav mr-auto header") {
                            li("nav-item") {
                                navLink(to = "/home", exact = true, className = "nav-link") {
                                    +"Home"
                                }
                            }

                            li("nav-item") {
                                navLink(to = "search", exact = true, className = "nav-link") {
                                    +"Search"
                                }
                            }
                        }
                    }
                }
                switch {
                    route("/home", Home::class)
                    route("/search", Search::class)
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}