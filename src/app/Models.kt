package home

data class Inspiration(
        val id: Long,
        val userId: Long,
        val fileUrl: String,
        val tags: Array<Tag>,
        val generatedDate: Long
)

data class Tag(
        val id: Long,
        val title: String
)
