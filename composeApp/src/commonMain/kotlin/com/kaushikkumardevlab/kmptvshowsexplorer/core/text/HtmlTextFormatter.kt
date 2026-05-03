package com.kaushikkumardevlab.kmptvshowsexplorer.core.text

object HtmlTextFormatter {

    fun toPlainText(value: String?): String? {
        if (value.isNullOrBlank()) return null

        return value
            .replace(blockTagRegex, "\n")
            .replace(tagRegex, "")
            .decodeHtmlEntities()
            .replace(whitespaceAroundLineBreakRegex, "\n")
            .replace(repeatedLineBreakRegex, "\n\n")
            .trim()
            .ifBlank { null }
    }

    private fun String.decodeHtmlEntities(): String {
        return replace("&nbsp;", " ")
            .replace("&amp;", "&")
            .replace("&quot;", "\"")
            .replace("&#39;", "'")
            .replace("&apos;", "'")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
    }

    private val blockTagRegex = Regex(
        pattern = "</?(p|br|div|section|article|ul|ol|li|h[1-6])\\b[^>]*>",
        options = setOf(RegexOption.IGNORE_CASE)
    )
    private val tagRegex = Regex("<[^>]+>")
    private val whitespaceAroundLineBreakRegex = Regex("[ \\t\\x0B\\f\\r]*\\n[ \\t\\x0B\\f\\r]*")
    private val repeatedLineBreakRegex = Regex("\\n{3,}")
}
