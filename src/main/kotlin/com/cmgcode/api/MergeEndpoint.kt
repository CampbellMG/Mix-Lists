package com.cmgcode.api

import com.cmgcode.data.LambdaRequest
import com.cmgcode.data.Response
import com.cmgcode.data.TrelloQueryParameters
import com.fasterxml.jackson.core.type.TypeReference
import com.julienvey.trello.domain.CheckList
import com.julienvey.trello.impl.TrelloImpl

class MergeEndpoint : TrelloEndpoint<TrelloQueryParameters, MergeEndpoint.MergeBody, Response>(
        object : TypeReference<LambdaRequest<TrelloQueryParameters>>() {},
        object : TypeReference<MergeBody>() {}
) {
    override fun getBody(trello: TrelloImpl, parameters: TrelloQueryParameters, body: MergeBody): Response {
        return body.validatedMergeBody.let {

            val checklist = trello.createCheckList(it.destinationCardId, CheckList())
            val items = it.checklistIds
                    .map { checklistId ->
                        trello.getCheckList(checklistId).checkItems
                    }
                    .flatten()
                    items.forEach { checkItem ->
                        try{
                            trello.createCheckItem(checklist.id, checkItem)
                        }catch (e: NullPointerException){
                            // Library messes up here
                        }

                    }

            Response()
        }
    }

    data class MergeBody(
            var destinationCardId: String? = null,
            var title: String? = null,
            var checklistIds: List<String>? = null
    ) {
        val validatedMergeBody: ValidatedMergeBody
            get() {
                return ValidatedMergeBody(
                        destinationCardId ?: throw IllegalArgumentException(),
                        title ?: throw IllegalArgumentException(),
                        checklistIds ?: throw IllegalArgumentException()
                )
            }

        data class ValidatedMergeBody(
                var destinationCardId: String,
                var title: String,
                var checklistIds: List<String>
        )
    }
}