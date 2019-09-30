package com.cmgcode.data

import com.cmgcode.api.Response

class ChecklistResponse(
        val checklists: Map<String, List<String>>
) : Response()