package com.cmgcode.data

open class TrelloRequest (
    var apiKey: String? = null,
    var token: String? = null
) {
    override fun toString(): String {
        return "TrelloRequest(apiKey=$apiKey, token$token)"
    }
}