package inspiration

import app.BASE_URL
import home.Inspiration
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.img

interface InspirationProps : RProps {
    var inspiration: Inspiration
}

class InspirationComponent(props: InspirationProps) : RComponent<InspirationProps, RState>(props) {
    override fun RBuilder.render() {
        img(
                classes = "card-img-top img-thumbnail",
                src = "$BASE_URL/users/1/inspirations/${props.inspiration.id}/images"
        ) {}
    }
}

fun RBuilder.inspiration(inspiration: Inspiration) = child(InspirationComponent::class) {
    attrs.inspiration = inspiration
}