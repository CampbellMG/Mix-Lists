package com.cmgcode.api

import org.junit.Test

class MergeEndpointTest {
    @Test
    fun name() {
        val request = mapOf(
                "queryStringParameters" to mapOf(
                        "apiKey" to "",
                        "token" to ""
                ),
                "body" to "{\n  \"destinationCardId\": \"\",\n  \"title\": \"Destination Title\",\n  \"checklistIds\": [\n    \"\",\n    \"\"   \n]\n}"
        )

        val response = MergeEndpoint().handleRequest(request, null)
    }
}