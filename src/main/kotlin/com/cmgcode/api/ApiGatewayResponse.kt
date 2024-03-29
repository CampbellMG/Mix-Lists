package com.cmgcode.api

import com.cmgcode.data.Response
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

class ApiGatewayResponse(
        val statusCode: Int = 200,
        var body: String? = null,
        val headers: Map<String, String>? = Collections.emptyMap()
) {

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var statusCode: Int = 200
        var headers: Map<String, String>? = CORS_HEADERS
        var rawBody: String? = null
        var objectBody: Response? = null

        fun build(): ApiGatewayResponse {
            try {
                return ApiGatewayResponse(
                        statusCode,
                        rawBody ?: ObjectMapper().writeValueAsString(objectBody),
                        headers
                )
            } catch (e: JsonProcessingException) {
                LOG.error("Failed to serialize object: ", e)
                throw RuntimeException(e)
            }
        }

        companion object {
            val LOG: Logger by lazy {
                LogManager.getLogger(Builder::class.java)
            }

            val CORS_HEADERS = mapOf(
                    "Access-Control-Allow-Origin" to "*",
                    "Access-Control-Allow-Credentials" to "true"
            )
        }
    }
}
