package com.cmgcode.api

import org.junit.Test

class ChecklistEndpointTest {
    @Test
    fun api() {
        val request = mapOf(
                "queryStringParameters" to mapOf(
                        "apiKey" to "",
                        "token" to "",
                        "boardId" to ""
                )
        )

        val response = ChecklistEndpoint().handleRequest(request, null)
        assert(response.body != null)
    }
}