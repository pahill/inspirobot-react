package search

import app.BASE_URL
import chip.chip
import home.Inspiration
import home.Tag
import inspiration.inspiration
import kotlinext.js.jsObject
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
import utils.AxiosConfigSettings
import utils.axios
import kotlin.browser.document
import kotlin.js.Date

interface SearchState : RState {
    var inspirations: List<Inspiration>
}

class Search : RComponent<RProps, SearchState>() {
    override fun SearchState.init() {
        inspirations = listOf()
    }

    private fun refreshImage(tagName: String) {
        findInspirations(tagName) {
            this@Search.setState {
                this.inspirations = it
            }
        }
    }

    override fun RBuilder.render() {
        div(classes = "m-2") {
            button(classes = "btn btn-primary d-inline-block float-right", type = ButtonType.button) {
                +"Search"
                attrs {
                    onClickFunction = {
                        val tagName = (document.getElementById("tag-name-input") as HTMLInputElement).value
                        refreshImage(tagName)
                    }
                }
            }
            input(classes = "form-control w-25 d-inline-block float-right", type = InputType.text) {
                attrs {
                    id = "tag-name-input"
                    placeholder = "Search by tag"
                }
            }
        }
        div("card-columns m-5") {
            if (!state.inspirations.isNullOrEmpty()) {
                state.inspirations.map { inspiration ->
                    div("card") {
                        inspiration(inspiration)
                        div("card-body") {
                            p {
                                small("text-muted") {
                                    val date = Date(inspiration.generatedDate)
                                    +date.toLocaleString()
                                }
                            }
                            val tags = inspiration.tags.toList()
                            if (!tags.isNullOrEmpty()) {
                                tags.map {
                                    chip(it.title)
                                }
                            } else {
                                p { +"No tags" }
                            }
                        }
                    }
                }
            } else {
                p { +"No results" }
            }
        }
    }

    private fun findInspirations(tag: String, callback: (List<Inspiration>) -> Unit) {
        val tagCallConfig: AxiosConfigSettings = jsObject {
            url = "$BASE_URL/tags?title=$tag"
            method = "GET"
            timeout = 3000
        }

        axios<Array<Tag>>(tagCallConfig).then { tags ->
            if (!tags.data.isNullOrEmpty()) {
                val firstTag = tags.data[0]
                val inspirationsCallConfig: AxiosConfigSettings = jsObject {
                    url = "$BASE_URL/users/1/inspirations?tagId=${firstTag.id}"
                    method = "GET"
                    timeout = 3000
                }

                axios<Array<Inspiration>>(inspirationsCallConfig).then { inspirations ->
                    callback(inspirations.data.toList())
                }
            } else {
                callback(emptyList())
            }
        }
    }
}
