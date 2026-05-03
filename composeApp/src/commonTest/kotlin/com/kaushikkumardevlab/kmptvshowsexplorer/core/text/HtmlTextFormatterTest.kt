package com.kaushikkumardevlab.kmptvshowsexplorer.core.text

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HtmlTextFormatterTest {

    @Test
    fun convertsHtmlSummaryToPlainText() {
        val html = "<p>A crew &amp; friends <b>explore</b> space.</p><p>New worlds&nbsp;await.</p>"

        assertEquals(
            "A crew & friends explore space.\n\nNew worlds await.",
            HtmlTextFormatter.toPlainText(html)
        )
    }

    @Test
    fun returnsNullForBlankInput() {
        assertNull(HtmlTextFormatter.toPlainText("   "))
    }
}
