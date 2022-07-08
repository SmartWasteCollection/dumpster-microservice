package swc.controllers.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "Dumpster not found")
class DumpsterNotFoundException(message: String) : Exception(message)
