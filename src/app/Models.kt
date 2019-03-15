package app

data class Inspiration(
        val id: Long,
        val userId: Long,
        val fileUrl: String,
        val tags: List<Tag>,
        val generatedDate: Long
)

data class Tag(
        val id: Long,
        val title: String
)
