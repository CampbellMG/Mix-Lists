package com.cmgcode.api

import com.cmgcode.data.ChecklistRequest
import com.cmgcode.data.ChecklistResponse
import com.julienvey.trello.impl.TrelloImpl

class Checklist : TrelloEndpoint<ChecklistRequest, ChecklistResponse>() {
    override fun getBody(trello: TrelloImpl, request: ChecklistRequest): ChecklistResponse = request.boardId?.let { boardId ->
        val checklists = trello
                .getBoardChecklists(boardId)
                .map {
                    trello.getCard(it.idCard).name to listOf(it.name)
                }
                .toMap()

        ChecklistResponse(checklists)
    } ?: throw IllegalArgumentException("Missing board ID")
}