package com.cmgcode.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class LambdaRequest<P>(
        var queryStringParameters: P? = null,
        var body: String = "{}"
)