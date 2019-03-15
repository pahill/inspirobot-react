package tageditor

import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import react.dom.input
import kotlin.browser.document

interface TagEditorProps : RProps {
    var callback: (String) -> Unit
}

class TagEditorComponent(props: TagEditorProps) : RComponent<TagEditorProps, RState>(props) {
    override fun RBuilder.render() {
        div("input-group mb-3") {
            input(classes = "form-control", type = InputType.text) {
                attrs {
                    id = "tag-name-input"
                }
            }
            div(classes = "input-group-append") {
                button(classes = "btn btn-primary", type = ButtonType.button) {
                    +"Add tag"
                    attrs {
                        onClickFunction = {
                            val tagName = (document.getElementById("tag-name-input") as HTMLInputElement).value
                            if (tagName.isNotBlank()) {
                                props.callback(tagName)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.tagEditor(callback: (String) -> Unit) = child(TagEditorComponent::class) {
    attrs.callback = callback
}