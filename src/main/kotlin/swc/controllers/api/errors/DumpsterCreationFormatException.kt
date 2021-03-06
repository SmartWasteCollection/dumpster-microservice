package swc.controllers.api.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(
    HttpStatus.NOT_FOUND,
    reason = "Dumpster creation format error: please provide a json object like the following. " +
        "{'dumpster': { 'capacity': double, 'wasteName': string }, 'collectionPointId': string }"
)
class DumpsterCreationFormatException(message: String) : Exception(message)
