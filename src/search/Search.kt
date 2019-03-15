package search

import chip.chip
import home.InspirationState
import inspiration.inspiration
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import react.dom.i
import react.dom.p
import tageditor.tagEditor

class Search : RComponent<RProps, RState>() {


    override fun RBuilder.render() {
        p { +"Searching stuff" }
    }
}
