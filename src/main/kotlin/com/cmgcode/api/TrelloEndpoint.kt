package com.cmgcode.api

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.cmgcode.data.ErrorResponse
import com.cmgcode.data.TrelloRequest
import com.julienvey.trello.impl.TrelloImpl
import com.julienvey.trello.impl.http.OkHttpTrelloHttpClient
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class TrelloEndpoint<I : TrelloRequest, O : Response> : RequestHandler<I, ApiGatewayResponse> {
    override fun handleRequest(input: I, context: Context?): ApiGatewayResponse {
        return safeLet(input.apiKey, input.token) { apiKey: String, token: String ->
            try {
                ApiGatewayResponse.build {
                    statusCode = 200
                    objectBody = getBody(TrelloImpl(apiKey, token, OkHttpTrelloHttpClient()), input)
                }
            } catch (e: Exception) {
                buildFailureResponse(e)
            }
        } ?: buildFailureResponse(IllegalArgumentException("Missing app key or token"))
    }

    private fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
        return try {
            if (p1 != null && p2 != null) block(p1, p2) else null
        } catch (e: Exception) {
            LOG.error("Failed to build result body", e)
            null
        }
    }

    private fun buildFailureResponse(e: Exception): ApiGatewayResponse {
        return ApiGatewayResponse.build {
            statusCode = 400
            objectBody = ErrorResponse(e.message ?: "Unknown Error")
        }
    }

    abstract fun getBody(trello: TrelloImpl, request: I): O

    companion object {
        val LOG: Logger by lazy {
            LogManager.getLogger(TrelloEndpoint::class.java)
        }
    }
}