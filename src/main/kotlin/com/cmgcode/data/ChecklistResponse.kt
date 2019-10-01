package com.cmgcode.data

data class ChecklistResponse(
        val checklists: Map<String, List<String>>
) : Response()