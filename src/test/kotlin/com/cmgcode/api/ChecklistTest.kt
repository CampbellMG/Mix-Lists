package com.cmgcode.api

import com.cmgcode.data.ChecklistRequest
import org.junit.Test

class ChecklistTest {
    @Test
    fun api() {
        val request = ChecklistRequest().apply {
            apiKey = ""
            token = ""
            boardId = ""
        }

        val response = Checklist().handleRequest(request, null)
        assert(response.body != null)
    }
}