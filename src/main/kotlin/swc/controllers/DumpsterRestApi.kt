package swc.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.entities.Dumpster
import swc.usecases.dumpster.CloseDumpsterUseCase
import swc.usecases.dumpster.GetDumpsterByIdUseCase
import swc.usecases.dumpster.GetDumpstersUseCase

@RestController
@RequestMapping("/dumpsters")
class DumpsterRestApi {

    @GetMapping
    fun getDumpsters(): List<Dumpster> = GetDumpstersUseCase().execute()

    @GetMapping("/{id}")
    fun getDumpsterById(@PathVariable id: String): Dumpster = GetDumpsterByIdUseCase(id).execute()

    @PutMapping("/close/{id}")
    fun closeDumpster(@PathVariable id: String): Dumpster = CloseDumpsterUseCase(id).execute()

    @PostMapping("/")
    fun createDumpster(@RequestBody dumpster: Dumpster) {
        println(dumpster)
    }
}
