package com.cmgcode.api

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.cmgcode.data.ErrorResponse
import com.cmgcode.data.LambdaRequest
import com.cmgcode.data.Response
import com.cmgcode.data.TrelloRequest
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.julienvey.trello.impl.TrelloImpl
import com.julienvey.trello.impl.http.JDKTrelloHttpClient
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class TrelloEndpoint<I : TrelloRequest, O : Response>(
        private val cls: TypeReference<LambdaRequest<I>>
) : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override fun handleRequest(input: Map<String, Any>, context: Context?): ApiGatewayResponse {
        return try {

            val request = getLambdaRequest(input)
            request.queryStringParameters?.let {
                safeLet(it.apiKey, it.token) { apiKey: String, token: String ->
                    val trello = TrelloImpl(apiKey, token, JDKTrelloHttpClient())
                    ApiGatewayResponse.build {
                        statusCode = 200
                        objectBody = getBody(trello, it)
                    }
                }
            } ?: buildFailureResponse(IllegalArgumentException("Missing required parameters"))

        } catch (e: Exception) {
            LOG.error(e)
            buildFailureResponse(e)
        }
    }

    private fun getLambdaRequest(input: Map<String, Any>): LambdaRequest<I> {
        val mapper = ObjectMapper()
        return mapper.convertValue(input, cls)
    }

    private fun buildFailureResponse(e: Exception): ApiGatewayResponse {
        return ApiGatewayResponse.build {
            statusCode = 400
            objectBody = ErrorResponse(e.message ?: "Unknown Error")
        }
    }

    private fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
        return if (p1 != null && p2 != null) block(p1, p2) else null
    }

    abstract fun getBody(trello: TrelloImpl, request: I): O

    companion object {
        val LOG: Logger by lazy {
            LogManager.getLogger(TrelloEndpoint::class.java)
        }
    }
}