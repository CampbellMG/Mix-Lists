package com.cmgcode.api

import com.cmgcode.data.LambdaRequest
import com.cmgcode.data.Response
import com.cmgcode.data.TrelloQueryParameters
import com.fasterxml.jackson.core.type.TypeReference
import com.julienvey.trello.impl.TrelloImpl

class ChecklistEndpoint : TrelloEndpoint<ChecklistEndpoint.ChecklistQueryParameters, Unit, ChecklistEndpoint.ChecklistResponse>(
        object : TypeReference<LambdaRequest<ChecklistQueryParameters>>() {},
        object : TypeReference<Unit>() {}
) {
    override fun getBody(trello: TrelloImpl, parameters: ChecklistQueryParameters, body: Unit): ChecklistResponse {
        return parameters.boardId?.let { boardId ->
            val cards = trello.getBoardCards(boardId)
                    .map {
                        it.id to it
                    }.toMap()

            val checklists = trello
                    .getBoardChecklists(boardId)
                    .map {
                        Checklist(
                                it.id,
                                it.name,
                                cards[it.idCard]?.name ?: "Unknown Card"
                        )
                    }

            ChecklistResponse(checklists)
        } ?: throw IllegalArgumentException("Missing board ID")
    }

    data class ChecklistQueryParameters(
            var boardId: String? = null
    ) : TrelloQueryParameters()

    data class ChecklistResponse(
            val checklists: List<Checklist>
    ) : Response()

    data class Checklist(
            val id: String,
            val name: String,
            val cardName: String
    )
}