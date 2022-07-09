package swc.controllers.api.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(
    HttpStatus.NOT_FOUND,
    reason = "Collection Point creation format error: please provide a json object like the following. " +
        "{'latitude': double, 'longitude': double }"
)
class CollectionPointCreationFormatException(message: String) : Exception(message)
