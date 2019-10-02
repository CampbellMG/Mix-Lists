package com.cmgcode.data

open class TrelloQueryParameters (
    var apiKey: String? = null,
    var token: String? = null
) {
    override fun toString(): String {
        return "TrelloQueryParameters(apiKey=$apiKey, token$token)"
    }
}