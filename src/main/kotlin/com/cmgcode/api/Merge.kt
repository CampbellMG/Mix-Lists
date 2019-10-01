package com.cmgcode.api

import com.cmgcode.data.LambdaRequest
import com.cmgcode.data.MergeRequest
import com.cmgcode.data.MergeResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.julienvey.trello.impl.TrelloImpl

class Merge : TrelloEndpoint<MergeRequest, MergeResponse>(
        object : TypeReference<LambdaRequest<MergeRequest>>() {}
) {
    override fun getBody(trello: TrelloImpl, request: MergeRequest): MergeResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}