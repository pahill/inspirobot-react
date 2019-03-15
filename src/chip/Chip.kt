package chip

import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import react.dom.i

interface ChipProps : RProps {
    var text: String
    var allowClose: Boolean
    var callback: (() -> Unit)?
}

class ChipComponent(props: ChipProps) : RComponent<ChipProps, RState>(props) {
    override fun RBuilder.render() {
        div("chip fade show") {
            +props.text
            if(props.allowClose) {
                button(classes = "close", type = ButtonType.button) {
                    attrs {
                        onClickFunction = { props.callback?.invoke() }
                    }
                    i(classes = "material-icons") { +"cancel" }
                }
            }
        }
    }
}

fun RBuilder.chip(text: String, allowClose: Boolean = false, callback: (() -> Unit)? = null) = child(ChipComponent::class) {
    attrs.text = text
    attrs.allowClose = allowClose
    attrs.callback = callback
}