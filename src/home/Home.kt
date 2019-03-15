package home

import chip.chip
import inspiration.inspiration
import kotlinext.js.jsObject
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import react.dom.i
import tageditor.tagEditor
import utils.AxiosConfigSettings
import utils.axios

const val BASE_URL = "http://0.0.0.0:8080"

interface InspirationState : RState {
    var inspiration: Inspiration?
    var tags: MutableList<String>
}

class Home : RComponent<RProps, InspirationState>() {
    override fun InspirationState.init() {
        refreshImage()
        tags = mutableListOf()
    }

    override fun RBuilder.render() {
        state.inspiration?.let {
            div("inspiro-card card mx-auto mb-5 mt-5") {
                inspiration(it)
                button(classes = "inspiro-btn-refresh btn btn-primary btn-float", type = ButtonType.button) {
                    attrs {
                        onClickFunction = { refreshImage() }
                    }
                    i(classes = "material-icons") { +"refresh" }
                }
                div("card-body") {
                    tagEditor { addChip(it) }
                    div("container") {
                        this@Home.state.tags.forEachIndexed { index, text -> chip(text) { removeChip(index) } }
                    }
                }
            }
        }
    }

    private fun refreshImage() {
        createInspiration {
            this@Home.setState {
                this.inspiration = it
                this.tags = mutableListOf()
            }
        }
    }

    private fun addChip(text: String) {
        val tags = state.tags
        tags.add(text)
        this.setState {
            this.tags = tags
        }
        updateInspiration(tags)
    }

    private fun removeChip(index: Int) {
        val tags = this.state.tags
        tags.removeAt(index)
        this.setState {
            this.tags = tags
        }
        updateInspiration(tags)
    }

    private fun createInspiration(callback: (Inspiration) -> Unit) {
        val callConfig: AxiosConfigSettings = jsObject {
            url = "$BASE_URL/users/1/inspirations"
            method = "POST"
            timeout = 3000
        }

        axios<Inspiration>(callConfig).then {
            callback(it.data)
        }
    }

    private fun updateInspiration(tags: List<String>) {
        val inspiration = this.state.inspiration
        if (inspiration != null) {
            val callConfig: AxiosConfigSettings = jsObject {
                url = "$BASE_URL/users/1/inspirations/${inspiration.id}"
                data = tags
                method = "PUT"
                timeout = 3000
            }

            axios<Inspiration>(callConfig).then {
                println(it.data)
            }
        }
    }
}

fun RBuilder.home() = child(Home::class) {}
