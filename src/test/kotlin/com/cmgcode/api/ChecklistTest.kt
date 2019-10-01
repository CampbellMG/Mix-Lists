package com.cmgcode.api

import com.cmgcode.data.ChecklistRequest
import com.cmgcode.data.LambdaRequest
import org.junit.Test

class ChecklistTest {
    @Test
    fun api() {
        val request = mapOf(
                "queryStringParameters" to mapOf(
                        "apiKey" to "",
                        "token" to "",
                        "boardId" to ""
                )
        )

        val response = Checklist().handleRequest(request, null)
        assert(response.body != null)
    }
}