package com.cmgcode.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class LambdaRequest<L>(
        var queryStringParameters: L? = null
)