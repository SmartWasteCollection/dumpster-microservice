package swc.controllers.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "CollectionPoint not found")
class CollectionPointNotFoundException(message: String) : Exception(message)
